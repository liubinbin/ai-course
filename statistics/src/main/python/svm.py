from sklearn.svm import SVC
from sklearn import datasets
from sklearn.model_selection import train_test_split
import load_data


def svm():

    # https://blog.csdn.net/szlcw1/article/details/52336824
    # SVC(C=1.0, cache_size=200, class_weight=None, coef0=0.0,
    #     decision_function_shape='ovr', degree=3, gamma='auto', kernel='rbf',
    #     max_iter=-1, probability=False, random_state=None, shrinking=True,
    #     tol=0.001, verbose=False)  # 可以根据前面介绍的参数，做出相应改变观察结果变化

    data = load_data.load_german_data_one_hot()
    xnparray = data['x']
    ynparray = data['y']

    x_train, x_test, y_train, y_test = train_test_split(xnparray, ynparray, test_size = 200, train_size = 800)

    # 'linear', 'poly', 'rbf', 'sigmoid', 'precomputed'
    # kernel = 'rbf'
    clf_rbf = SVC(kernel='rbf')
    clf_rbf.fit(x_train,y_train)
    score_rbf = clf_rbf.score(x_test,y_test)
    print("The score of rbf is : %f"%score_rbf)

    # kernel = 'linear'
    clf_linear = SVC(kernel='linear')
    clf_linear.fit(x_train,y_train)
    score_linear = clf_linear.score(x_test,y_test)
    print("The score of linear is : %f"%score_linear)

    # kernel = 'poly'
    clf_poly = SVC(kernel='poly')
    clf_poly.fit(x_train,y_train)
    score_poly = clf_poly.score(x_test,y_test)
    print("The score of poly is : %f"%score_poly)

    # kernel = 'sigmoid'
    clf_poly = SVC(kernel='sigmoid')
    clf_poly.fit(x_train,y_train)
    score_poly = clf_poly.score(x_test,y_test)
    print("The score of sigmoid is : %f"%score_poly)


if  __name__ == "__main__":
    svm()