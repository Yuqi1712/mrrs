<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('subject:subject:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('subject:subject:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="影片id">
      </el-table-column>
      <el-table-column
        prop="nameCn"
        header-align="center"
        align="center"
        label="中文名">
      </el-table-column>
      <el-table-column
        prop="nameUs"
        header-align="center"
        align="center"
        label="英文名">
      </el-table-column>
      <el-table-column
        prop="playDesc"
        header-align="center"
        align="center"
        label="剧情简介">
      </el-table-column>
      <el-table-column
        prop="datePublished"
        header-align="center"
        align="center"
        label="上映日期">
      </el-table-column>
      <el-table-column
        prop="genre"
        header-align="center"
        align="center"
        label="影片类型">
      </el-table-column>
      <el-table-column
        prop="label"
        header-align="center"
        align="center"
        label="影片标签">
      </el-table-column>
      <el-table-column
        prop="rating"
        header-align="center"
        align="center"
        label="影片评分">
      </el-table-column>
      <el-table-column
        prop="playVotes"
        header-align="center"
        align="center"
        label="评分人数">
      </el-table-column>
      <el-table-column
        prop="otherName"
        header-align="center"
        align="center"
        label="别民">
      </el-table-column>
      <el-table-column
        prop="playActor"
        header-align="center"
        align="center"
        label="演员列表">
      </el-table-column>
      <el-table-column
        prop="director"
        header-align="center"
        align="center"
        label="导演列表">
      </el-table-column>
      <el-table-column
        prop="playWriter"
        header-align="center"
        align="center"
        label="编剧列表">
      </el-table-column>
      <el-table-column
        prop="country"
        header-align="center"
        align="center"
        label="制片国家">
      </el-table-column>
      <el-table-column
        prop="language"
        header-align="center"
        align="center"
        label="语言">
      </el-table-column>
      <el-table-column
        prop="playImg"
        header-align="center"
        align="center"
        label="影片海报">
      </el-table-column>
      <el-table-column
        prop="subjectType"
        header-align="center"
        align="center"
        label="影片类型  MOVIE:电影
TVPLAY:电视剧

">
      </el-table-column>
      <el-table-column
        prop="number"
        header-align="center"
        align="center"
        label="电视剧集数">
      </el-table-column>
      <el-table-column
        prop="duration"
        header-align="center"
        align="center"
        label="影片时长">
      </el-table-column>
      <el-table-column
        prop="version"
        header-align="center"
        align="center"
        label="乐观锁版本">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
      </el-table-column>
      <el-table-column
        prop="updateTime"
        header-align="center"
        align="center"
        label="修改时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './subject-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/subject/subject/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/subject/subject/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
