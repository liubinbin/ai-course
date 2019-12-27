from sklearn.svm import SVC
from sklearn.model_selection import train_test_split
import load_data
from score import cal_score
from sklearn.externals import joblib
import warnings
warnings.filterwarnings("ignore")


def svm():

    # https://blog.csdn.net/szlcw1/article/details/52336824
    # SVC(C=1.0, cache_size=200, class_weight=None, coef0=0.0,
    #     decision_function_shape='ovr', degree=3, gamma='auto', kernel='rbf',
    #     max_iter=-1, probability=False, random_state=None, shrinking=True,
    #     tol=0.001, verbose=False)  # 可以根据前面介绍的参数，做出相应改变观察结果变化

    data = load_data.load_data_s1()
    xnparray = data['x']
    ynparray = data['y']

    x_train, x_test, y_train, y_test = train_test_split(xnparray, ynparray, test_size = 200, train_size = 800, shuffle=False)

    # 'poly', 'rbf', 'sigmoid', 'linear'
    for kernal in ['linear']:
        print("kernal: {}".format(kernal))
        clf = SVC(kernel=kernal)
        clf.fit(x_train,y_train)
        score_rbf = clf.score(x_test,y_test)
        print("The score of kernal {} is : {}".format(kernal, score_rbf))
        # clf = joblib.load('svm.pkl')
        y_test_predict = clf.predict(x_test)
        joblib.dump(clf, 'svm.pkl')
        dict = cal_score(y_test_predict, y_test)
        print("precision: {} recall: {} f1 {}".format(dict['precision'], dict['recall'], dict['f1']))


if  __name__ == "__main__":
    svm()