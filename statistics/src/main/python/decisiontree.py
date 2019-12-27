from sklearn import tree
from sklearn.model_selection import train_test_split
import numpy as np
import load_data
from sklearn.metrics import accuracy_score
from sklearn.metrics import recall_score
from sklearn.externals import joblib
from score import cal_score


def decision_tree():

    data = load_data.load_data_d1()

    xnparray = data['x']
    ynparray = data['y']

    x_train, x_test, y_train, y_test = train_test_split(xnparray, ynparray, test_size = 200, train_size = 800, shuffle=False)

    print("x_train: " + str(len(x_train)) + ", x_test: " + str(len(x_test)) + ", y_train: " + str(len(y_train)) + ", y_test: " + str(len(y_test)))

    dtc = tree.DecisionTreeClassifier(criterion="gini", min_samples_split=6)
    clf = dtc.fit(x_train, y_train)
    # clf = joblib.load('decisiontree.pkl')
    y_test_predict = clf.predict(x_test)
    joblib.dump(clf, 'decisiontree.pkl')

    dict = cal_score(y_test_predict, y_test)
    print("precision: {} recall: {} f1 {}".format(dict['precision'], dict['recall'], dict['f1']))


if  __name__ == "__main__":
    decision_tree()