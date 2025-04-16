import pyodbc
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sqlalchemy import create_engine

# 数据库配置
db_config = {
    'Driver': 'ODBC Driver 17 for SQL Server',
    'Server': 'localhost',
    'Database': 'Your_Database_Name',
    'UID': 'sa',
    'PWD': 'Your_Database_Password'
}
connection_string = (
    "mssql+pyodbc://{UID}:{PWD}@{Server}/{Database}?"
    "driver={Driver}".format(
        UID=db_config['UID'],
        PWD=db_config['PWD'],
        Server=db_config['Server'],
        Database=db_config['Database'],
        Driver=db_config['Driver'].replace(' ', '+')
    )
)
engine = create_engine(connection_string)
df = pd.read_sql("SELECT X, Y FROM point", engine)
points = df[['X', 'Y']].values

# 设置交互模式
plt.ion()
fig = plt.figure(figsize=(8, 6))

def initialize_centroids(points, k):
    indices = np.random.choice(len(points), k, replace=False)#从点索引中随机选取K个不重复的索引
    return points[indices]

def assign_clusters(points, centroids):# 计算每个点到所有质心的欧氏距离
    distances = np.sqrt(((points[:, np.newaxis] - centroids)**2).sum(axis=2))
    return np.argmin(distances, axis=1)

def update_centroids(points, labels, k):
    new_centroids = np.zeros((k, 2))
    for i in range(k):
        cluster_points = points[labels == i]# 获取第i簇的所有点
        if len(cluster_points) > 0:
            new_centroids[i] = cluster_points.mean(axis=0)# 计算均值
        else:  # 处理空簇
            new_centroids[i] = points[np.random.choice(len(points))]
    return new_centroids

# 主算法
k = int(input("请输入聚类数量k: "))
centroids = initialize_centroids(points, k)
labels = assign_clusters(points, centroids)

iteration = 0 #记录当前算法已经执行的迭代次数
max_iterations = 50 #最大可迭代次数
changed = True #可迭代标志，当新旧簇标签完全相同时被修改为false

while changed and iteration < max_iterations:
    # 绘制当前状态
    plt.clf()
    plt.scatter(points[:, 0], points[:, 1], c=labels, cmap='tab10', alpha=0.7)
    plt.scatter(centroids[:, 0], centroids[:, 1], c='red', s=200, marker='X')
    plt.title(f'Iteration {iteration}')
    plt.draw()
    plt.pause(1)
    

    new_centroids = update_centroids(points, labels, k)#遍历，计算这些点的坐标平均值作为新质心
    new_labels = assign_clusters(points, new_centroids)
    changed = not np.array_equal(labels, new_labels)# 检查是否收敛
    labels = new_labels
    centroids = new_centroids
    iteration += 1

plt.ioff()
plt.show()