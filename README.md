# mrrs
基于微服务的影评与智能推荐系统--mrrs

renren-fast-vue:
环境搭建选用 
node.js 10
node-sass 4.13.1
node.js 和node-sass版本需对应或兼容
python 2.7
更换sass源 npm config set sass_binary_site=https://npm.taobao.org/mirrors/node-sass

mrrs:
Springboot版本需在2.2.2以下(2.2.2可用  2.4.2与当前使用的Spring Cloud组件不兼容)

由于mybatis-plus主键字段生成策略用雪花算法生成19位Long数据，前端js无法接收这么大，最后两位数会出现精度丢失变为0，解决策略：使用	@JsonFormat(shape = JsonFormat.Shape.STRING)注解标注在要返回实体的id上，将Long值转换为String返回

Springboot2.2.2会将RestHighLevelClient7.6.2中Es的版本配置为6.8.x启动时会报错不兼容，将springboot版本改变为2.3.3

mrrs-front:
mrrs前端项目采用vue-cli 2.x版本进行项目创建
npm install -g @vue/cli-init
# `vue init` 的运行效果将会跟 `vue-cli@2.x` 相同
vue init webpack my-project