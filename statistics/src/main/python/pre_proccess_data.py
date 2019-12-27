

SRC_ATTR_SEPARATOR = ' '
TARGET_ATTR_SEPARATOR = ','
ONE_FLAG = '1'
ZERO_FLAG = '0'


def attr_2_onehot(attr, candidates):
    onehot_attr = ''
    for i in range(len(candidates)):
        if i != 0:
            onehot_attr += TARGET_ATTR_SEPARATOR
        if attr == candidates[i]:
            onehot_attr += ONE_FLAG
        else:
            onehot_attr += '0'
    return onehot_attr


def range_2_onehot(attr, candidates):
    onehot_attr = ''
    attr_in_num = int(attr)
    for i in range(len(candidates)):
        if i != 0:
            onehot_attr += TARGET_ATTR_SEPARATOR
        if i == 0:
            if attr_in_num < candidates[i]:
                onehot_attr += ONE_FLAG
            else:
                onehot_attr += ZERO_FLAG
            onehot_attr += TARGET_ATTR_SEPARATOR
            if attr_in_num >= candidates[i] and attr_in_num < candidates[i+1]:
                onehot_attr += ONE_FLAG
            else:
                onehot_attr += ZERO_FLAG
        elif i == len(candidates) - 1:
            if attr_in_num >= candidates[i]:
                onehot_attr += ONE_FLAG
            else:
                onehot_attr += ZERO_FLAG
        else:
            if attr_in_num >= candidates[i] and attr_in_num < candidates[i+1]:
                onehot_attr += ONE_FLAG
            else:
                onehot_attr += ZERO_FLAG
    return onehot_attr


if __name__ == '__main__':
    # print(attr_2_onehot('2', ['1', '2']))
    # print(range_2_onehot('401', [100, 200, 300, 400]))
    src_data_path = "C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data"
    target_data_path = "C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data.onehot.k1"
    src_file = open(src_data_path)
    target_file = open(target_data_path, mode='w')

    while 1:
        line = src_file.readline()
        if not line:
            break
        line = line.strip()
        # print(line)
        attrs = line.split(SRC_ATTR_SEPARATOR)
        outline = ""
        outline += (attr_2_onehot(attrs[0], ["A11", "A12", "A13", "A14"]) + TARGET_ATTR_SEPARATOR)
        outline += (attrs[1] + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[2], ["A30", "A31", "A32", "A33", "A34"]) + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[3], ["A40", "A41", "A42", "A43", "A44", "A45", "A46", "A47", "A48", "A49", "A410"]) + TARGET_ATTR_SEPARATOR)
        # outline += (attr_2_onehot(attrs[4], [2000, 4000, 6000, 8000, 10000]) + TARGET_ATTR_SEPARATOR)
        outline += (attrs[4] + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[5], ["A61", "A62", "A63", "A64", "A65"]) + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[6], ["A71", "A72", "A73", "A74", "A75"]) + TARGET_ATTR_SEPARATOR)
        outline += (attrs[7] + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[8], ["A91", "A92", "A93", "A94", "A95"]) + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[9], ["A101", "A102", "A103"]) + TARGET_ATTR_SEPARATOR)
        outline += (attrs[10] + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[11], ["A121", "A122", "A123", "A124"]) + TARGET_ATTR_SEPARATOR)
        outline += (attrs[12] + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[13], ["A141", "A142", "A143"]) + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[14], ["A151", "A152", "A153"]) + TARGET_ATTR_SEPARATOR)
        outline += (attrs[15] + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[16], ["A171", "A172", "A173", "A174"]) + TARGET_ATTR_SEPARATOR)
        outline += (attrs[17] + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[18], ["A191", "A192"]) + TARGET_ATTR_SEPARATOR)
        outline += (attr_2_onehot(attrs[19], ["A201", "A202"]) + TARGET_ATTR_SEPARATOR)
        outline += (attrs[20])
        # outline += (attr_2_onehot(attrs[20], ["1", "2"]))
        # print(outline)
        target_file.write(outline + "\n")
    target_file.close()
    src_file.close()

    # print("hello pre_process_data")
