from sklearn import tree
from sklearn.model_selection import train_test_split
import numpy as np
import load_data

def decision_tree():

    data = load_data.load_german_data()
    # print(data)
    xnparray = data['x']
    ynparray = data['y']

    # print(len(xnparray))
    # print(len(ynparray))

    x_train, x_test, y_train, y_test = train_test_split(xnparray, ynparray, test_size = 200, train_size = 800)
    print("x_train: " + str(len(x_train)) + ", x_test: " + str(len(x_test)) + ", y_train: " + str(len(y_train)) + ", y_test: " + str(len(y_test)))

    dtc = tree.DecisionTreeClassifier(criterion="entropy")
    clf = dtc.fit(x_train, y_train)
    y_predict = clf.predict(x_test)

    succ_count = 0
    for i in range(len(y_test)):
        if (y_test[i][0] == y_predict[i]):
            # print("success")
            succ_count = succ_count + 1
        # else:
        #     print(str(y_test[i][0]) + " " + str(y_predict[i]))
    print("succ_count: ", succ_count , " succ_rate: %d", succ_count/len(y_test))


if  __name__ == "__main__":
    decision_tree()