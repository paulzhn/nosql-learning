# Counter Service 简要说明

## 简介

封装了`num`, `set`两种数据类型，并且在其上实现了`freq`统计：

- 对于`set`，`retrieve`会取出 set 的成员数量；save 会向 set 中插入新的成员。
- 对于`num`，`retrieve`会取出对应 key 的值；save 则会按照`valueFields`中指定的数量，incr 相应的 key。
- 对于`freq`，在`num`上会返回`from` 到 `to` 的 key 对应的 value 求和；而`set`会返回对应的 value 的成员数量求和。

此外，也实现了 json 修改监听，只是监听路径是通过 `getResource` 取的，所以目前只能监听产物中的 json 文件。