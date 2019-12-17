from sklearn.model_selection import train_test_split # 分割数据模块
from sklearn.neighbors import KNeighborsClassifier # K最近邻(kNN，k-NearestNeighbor)分类算法
import matplotlib.pyplot as plt #可视化模块
import load_data


def knn():

    data = load_data.load_german_data_one_hot()
    xnparray = data['x']
    ynparray = data['y']

    x_train, x_test, y_train, y_test = train_test_split(xnparray, ynparray, test_size = 200, train_size = 800)

    k_range = range(1, 101)
    k_scores = []

    for i in range(1, 101):

        knn = KNeighborsClassifier(n_neighbors=i)

        knn.fit(x_train, y_train)

        score = knn.score(x_test, y_test)
        print(score)
        k_scores.append(score)

    print(k_range)
    print(k_scores)

    #可视化数据
    plt.plot(k_range, k_scores)
    plt.xlabel('Value of K for KNN')
    plt.ylabel('Cross-Validated Accuracy')
    plt.show()


if  __name__ == "__main__":
    knn()