# ListWrap

注意:github上编辑文件需要在该行末尾添加两个空格

本工程主要提供了一个List的包装工具类，能够弥补List不能够默认初始化的不足

用法：  
  ListWrap<Integer> listWrap = new ListWrap<>(0);  
  listWrap.add(4,4);

其中：add用于向List中指定的位置添加元素，如上,向List的索引为4的位置添加了一个元素4,  
其未为指定元素的索引位置,使用默认值0,进行初始化

该List包装类用途之一：主要是在对前端返回图表类数据结构时，有些时候需要对查询数据时，没有数据的位置补0,  
就可以通过该工具类方便实现
  
  
