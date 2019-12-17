import numpy as np

def load_german_data(return_X_y=False):

    x = []
    y = []

    data_path = "C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data"
    f = open(data_path)
    line = f.readline()
    while line:
        # print(line)
        attr = line.replace("\n", "").replace("A", "").split(" ")

        x.append(attr[:20])
        y.append(attr[20:])
        line = f.readline()
    f.close()

    xnparray = np.array(x)
    ynparray = np.array(y)

    dict = {}
    dict['x'] = xnparray
    dict['y'] = ynparray

    return dict


def load_german_data_one_hot(return_X_y=False):

    x = []
    y = []

    data_path = "C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data.onehot"
    f = open(data_path)
    line = f.readline()
    while line:
        # print(line)
        attr = line.replace("\n", "").split(",")
        # print(len(attr))
        # print(attr[:63])
        # print(attr[63:])

        x.append(attr[:63])
        y.append(attr[63:])
        line = f.readline()
    f.close()

    xnparray = np.array(x)
    ynparray = np.array(y)

    dict = {}
    dict['x'] = xnparray
    dict['y'] = ynparray

    return dict


if __name__ == "__main__":
    load_german_data_one_hot()