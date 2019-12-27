import numpy as np


BAD_FLAG = '2'

def cal_score(y_test_predict, y_test):
    dict = {}

    # (1 = Good, 2 = Bad)
    # precision=被预测为违约的人当中有多大比例真正违约（TP/(TP+FP)），
    # recall=真正违约的人当中有多大比例被预测为违约（(TP)/（TP+FN））
    y_test_list = y_test.tolist()
    y_test_predict_list = y_test_predict.tolist()

    # print(y_predict_list)
    # print(y_test_list)

    tp = 0
    tp_and_fp = 0
    fp = 0
    fp_and_fn = 0

    for i in range(len(y_test_predict_list)):
        if y_test_predict_list[i] == BAD_FLAG:
            tp_and_fp = tp_and_fp + 1
            if y_test_list[i][0] == BAD_FLAG:
                tp = tp + 1
    for i in range(len(y_test_predict_list)):
        if y_test_list[i][0] == BAD_FLAG:
            fp_and_fn = fp_and_fn + 1
            if y_test_predict_list[i] == BAD_FLAG:
                fp = fp + 1

    precision = tp / tp_and_fp
    recall = fp / fp_and_fn
    f1 = (2 * precision * recall) / (precision + recall)
    dict['precision'] = precision
    dict['recall'] = recall
    dict['f1'] = f1
    print("tp: {} tp_and_fp {} precision: {} fp {} fp_and_fn {} recall {} f1 {}".format(tp, tp_and_fp, tp / tp_and_fp, fp, fp_and_fn, fp / fp_and_fn, f1))
    return dict


if __name__ == "__main__":
    pass
    # result = cal_evaluation_criterion()
    # print(result)