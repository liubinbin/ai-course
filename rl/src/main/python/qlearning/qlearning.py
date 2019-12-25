

# reward值的矩阵R
r = [[0 for i in range(6)] for i in range(6)]
# 动作价值函数
q = [[0 for i in range(6)] for i in range(6)]

factor = 0.8
round = 1


def init():
    r[0] = [-1, -1, -1, -1, 0, -1]
    r[1] = [-1, -1, -1, 0, -1, 100]
    r[2] = [-1, -1, -1, 0, -1, -1]
    r[3] = [-1, 0, 0, -1, 0, -1]
    r[4] = [0, -1, -1, 0, -1, 100]
    r[5] = [-1, 0, -1, -1, 0, 100]


def one_round():
    newq = [[0 for i in range(6)] for i in range(6)]
    # print(newq)
    global q
    for i in range(len(q)):
        for j in range(len(q[0])):
            newq[i][j] = max(r[i][j] + (factor * max(q[j])), 0)
            print("i: {}  j: {}  r: {}  max: {} newq: {}".format(i, j, r[i][j], max(q[j]), newq[i][j]))
            # print(newq)
    # print(newq)
    q = newq


def main():
    init()
    print("hello q-learning")
    global q
    print(q)
    roundi = 0
    while roundi < round:
        print("roundi {}".format(roundi))
        one_round()
        print(q)
        roundi = roundi + 1


if __name__ == '__main__':
    main()
