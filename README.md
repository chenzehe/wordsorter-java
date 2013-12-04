支持 http://ifeve.com/tao-code-match-1/ ，用fork-join来实现 <p />
读取一个文件中的单词(使用BufferedReader按行读取)，排序(使用fork-join框架快速排序)，写到另一个文件中(使用BufferedWriter進行寫入)  <p />
运行平均速度为： <br />
read file:78ms <br />
sort:141ms <br />
write file:78ms <br />
total:297ms 
<p />
比楼主的快点：<p />
Loading contents of E:\chenzehe\workspace\WordSorter\src\sowpods.txt... 234ms
Sorting... 484ms
Writing results to E:\chenzehe\workspace\WordSorter\src\out.txt... 124ms

Using 128 threads, 267751 words was sorted in 842 milliseconds.
