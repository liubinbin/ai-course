from sklearn.model_selection import train_test_split # 分割数据模块
from sklearn.neighbors import KNeighborsClassifier # K最近邻(kNN，k-NearestNeighbor)分类算法
import matplotlib.pyplot as plt #可视化模块
import load_data
from score import cal_score
from sklearn.externals import joblib
import warnings
warnings.filterwarnings("ignore")

def knn():

    data = load_data.load_data_k1()
    xnparray = data['x']
    ynparray = data['y']

    x_train, x_test, y_train, y_test = train_test_split(xnparray, ynparray, test_size = 200, train_size = 800, shuffle=False)

    for i in range(1, 2):
        print("n_neighbors {}".format(i))
        clf = KNeighborsClassifier(n_neighbors=i)
        clf.fit(x_train, y_train)
        # clf = joblib.load('knn.pkl')
        y_test_predict = clf.predict(x_test)
        joblib.dump(clf, 'knn.pkl')
        dict = cal_score(y_test_predict, y_test)
        print("precision: {} recall: {} f1 {}".format(dict['precision'], dict['recall'], dict['f1']))

    #可视化数据
    # plt.plot(k_range, k_scores)
    # plt.xlabel('Value of K for KNN')
    # plt.ylabel('Cross-Validated Accuracy')
    # plt.show()


if  __name__ == "__main__":
    knn()