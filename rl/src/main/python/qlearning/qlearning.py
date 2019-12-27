import random


# reward
r = [[0 for i in range(6)] for i in range(6)]
# move value function
q = [[0 for i in range(6)] for i in range(6)]
# option for a move
m = [[] for i in range(6)]
ROUND_COUNT = 500
END_POINT = 5
GAMA = 0.8
# init r
r[0] = [-1, -1, -1, -1, 0, -1]
r[1] = [-1, -1, -1, 0, -1, 100]
r[2] = [-1, -1, -1, 0, -1, -1]
r[3] = [-1, 0, 0, -1, 0, -1]
r[4] = [0, -1, -1, 0, -1, 100]
r[5] = [-1, 0, -1, -1, 0, 100]
# init m
m[0] = [4]
m[1] = [3, 5]
m[2] = [3]
m[3] = [1, 2, 4]
m[4] = [0, 3, 5]
m[5] = [1, 4, 5]


'''
 generate a epsoide and update q
'''
def one_round():
    start_point = random.randint(0, 5)
    cur_point = start_point
    # we should take a move if pick point 5 for the start_point.
    # so we set fist to True to force to take a move whatever we pick for the start_point.
    first = True
    while first or cur_point != END_POINT:
        # pick next_point for a move
        next_point_idx = random.randint(0, len(m[cur_point]) - 1)
        # print("cur_point {} len {} next_point_idx {}".format(cur_point, len(m[cur_point]), next_point_idx))
        next_point = m[cur_point][next_point_idx]
        # update q
        q[cur_point][next_point] = r[cur_point][next_point] + (GAMA * max(q[next_point]))
        # update cur_point
        cur_point = next_point
        first = False


def print_q():
    print("q function")
    for i in range(6):
        print(q[i])


def normalize_q():
    q_max = 0
    for i in range(0, 6):
        q_max = max(q_max, max(q[i]))
    # print(q_max)
    for i in range(0, 6):
        for j in range(0, 6):
            q[i][j] = round(( q[i][j] / q_max ) * 100)


def q_learning():
    round_idx = 0
    while round_idx < ROUND_COUNT:
        # print(random.randint(0, 5))
        print("round_idx {}".format(round_idx))
        one_round()
        print_q()
        round_idx = round_idx + 1
    print("start to normalize_q")
    normalize_q()


def choose_path(start_point):
    print("path for {}".format(start_point))
    if start_point == END_POINT:
        print(start_point)
    else:
        cur_point = start_point
        print(start_point, end="")
        cur_point = q[cur_point].index(max(q[cur_point]))
        while cur_point != END_POINT:
            print(" -> {}".format(cur_point), end="")
            cur_point = q[cur_point].index(max(q[cur_point]))
        print(" -> {}".format(cur_point))


if __name__ == '__main__':
    q_learning()
    print_q()
    choose_path(2)