from sklearn import tree
from sklearn.model_selection import train_test_split
import numpy as np
import load_data
from sklearn.metrics import accuracy_score
from sklearn.metrics import recall_score
from sklearn.externals import joblib
from score import cal_score_float
from sklearn.naive_bayes import GaussianNB
from sklearn.naive_bayes import MultinomialNB
from sklearn.naive_bayes import BernoulliNB
import warnings
warnings.filterwarnings("ignore")

def nb():

    data = load_data.load_data_n1()

    xnparray = data['x']
    ynparray = data['y']

    x_train, x_test, y_train, y_test = train_test_split(xnparray, ynparray, test_size = 200, train_size = 800, shuffle=False)

    print("x_train: " + str(len(x_train)) + ", x_test: " + str(len(x_test)) + ", y_train: " + str(len(y_train)) + ", y_test: " + str(len(y_test)))
    # print(x_train)
    # print(y_train)

    clf = GaussianNB()
    # clf = MultinomialNB()
    # clf = BernoulliNB()
    clf = clf.fit(x_train, y_train)

    y_test_predict = clf.predict(x_test)
    joblib.dump(clf, 'nb.pkl')

    # print(y_test_predict)
    dict = cal_score_float(y_test_predict, y_test)
    print("precision: {} recall: {} f1 {}".format(dict['precision'], dict['recall'], dict['f1']))


if  __name__ == "__main__":
    nb()