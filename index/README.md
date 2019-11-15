# 程序组成

程序分两部分:

1. python 部分为爬虫模块代码。

   爬取时间较长，目前已爬完放在 data 目录下。

2. java 部分为索引模块代码。

# 程序参数

cn.liubinbin.ai.index.limit 为最终检索输出的最多条数

cn.liubinbin.ai.index.showType 为显示的类型，共三种：id，title，和 content（content 暂时不支持，索引篇数太多，content 无由于内存问题无法索引进去）

cn.liubinbin.ai.index.newsCount  为索引的篇数，最多 10000。

# 运行注意事项

最好直接指定 java 的内存参数 “-Xmx1024m -Xms1024m”，否则在 jdk 扩容过程比较慢。

# 支持检索方式

1. 单个索引词
2. 两个索引词，中间使用 AND，OR 相连