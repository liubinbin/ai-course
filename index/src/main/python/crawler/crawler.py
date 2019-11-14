import requests
import json
from bs4 import BeautifulSoup


def crawler(data_path):
    # 获取 url
    urls = []
    urli = 0
    url_format = "http://feed.mix.sina.com.cn/api/roll/get?pageid=155&lid=1686&num=10&page={}&callback=feedCardJsonpCallback&_=1573695351278";
    for page in range(1, 1001):
        temp_url = url_format.format(page)
        req = requests.get(temp_url)
        req_json_str = str(req.content[26:][:-14], encoding = "utf-8")
        # print(json.loads(req_json_str)['result']['data'])
        datas = json.loads(req_json_str)['result']['data']
        for data in datas:
            # print(data['url'])
            urls.append(data['url'])
            print("get url " + str(urli))
            urli += 1
    print(urls.__len__())

    # 按每个 url 抓取，解析并存储
    urli = 1
    for url in urls:
        # print(url)
        page = requests.get(url)
        page.encoding = 'utf-8'
        soup = BeautifulSoup(str(page.text), 'html.parser')
        title = soup.title.text
        content = soup.select('#artibody')[0].text
        # print(title)
        # print(content)
        temp_data_path = data_path + "\\" + str(urli);
        urli += 1;
        print("crawl & save " + str(urli))
        # print(temp_data_path)
        with open(temp_data_path, encoding='utf8', mode='w') as file:
            file.write(title)
            file.write(str(content))

if __name__ == "__main__":
    data_path = "D:\\src\\ai-course\\index\\data";
    crawler(data_path)
