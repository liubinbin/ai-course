from sklearn import tree
from sklearn.model_selection import train_test_split
import numpy as np
import load_data
from sklearn.metrics import accuracy_score
from sklearn.metrics import recall_score
from sklearn.externals import joblib
from score import cal_score_float

from sklearn.ensemble import RandomForestClassifier
from sklearn.datasets import make_gaussian_quantiles
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

    clf = RandomForestClassifier(n_estimators=10, criterion='gini', max_depth=None,
        min_samples_split=2, min_samples_leaf=1,
        min_weight_fraction_leaf=0.0,
        max_features='auto',
        max_leaf_nodes=None, bootstrap=True,
        oob_score=True, n_jobs=1, random_state=10, verbose=0,
        warm_start=False, class_weight=None)
    clf.fit(x_train, y_train)

    y_test_predict = clf.predict(x_test)
    joblib.dump(clf, 'randomforst.pkl')

    # print(y_test_predict)
    dict = cal_score_float(y_test_predict, y_test)
    print("precision: {} recall: {} f1 {}".format(dict['precision'], dict['recall'], dict['f1']))


if  __name__ == "__main__":
    nb()