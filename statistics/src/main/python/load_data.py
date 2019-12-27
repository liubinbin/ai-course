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


def load_data_d1():
    return load_german_data()


def load_data_k1():
    return load_german_data_one_hot("C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data.k1", 63)


def load_data_s1():
    return load_german_data_one_hot("C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data.s1", 63)


def load_german_data_one_hot(data_path, split_pos):

    x = []
    y = []

    # data_path = "C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data.onehot.k1"
    f = open(data_path)
    line = f.readline()
    while line:
        # print(line)
        attr = line.replace("\n", "").split(",")
        # print(len(attr))
        # print(attr[:split_pos])
        # print(attr[split_pos:])
        x.append(attr[:split_pos])
        y.append(attr[split_pos:])
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