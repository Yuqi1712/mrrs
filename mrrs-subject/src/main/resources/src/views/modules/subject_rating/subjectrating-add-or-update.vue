<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="影片id" prop="subjectId">
      <el-input v-model="dataForm.subjectId" placeholder="影片id"></el-input>
    </el-form-item>
    <el-form-item label="影片名称" prop="subjectName">
      <el-input v-model="dataForm.subjectName" placeholder="影片名称"></el-input>
    </el-form-item>
    <el-form-item label="评分" prop="ratingScore">
      <el-input v-model="dataForm.ratingScore" placeholder="评分"></el-input>
    </el-form-item>
    <el-form-item label="五星评级数" prop="ratingA">
      <el-input v-model="dataForm.ratingA" placeholder="五星评级数"></el-input>
    </el-form-item>
    <el-form-item label="四星评级数" prop="ratingB">
      <el-input v-model="dataForm.ratingB" placeholder="四星评级数"></el-input>
    </el-form-item>
    <el-form-item label="三星评级数" prop="ratingC">
      <el-input v-model="dataForm.ratingC" placeholder="三星评级数"></el-input>
    </el-form-item>
    <el-form-item label="二星评级数" prop="ratingD">
      <el-input v-model="dataForm.ratingD" placeholder="二星评级数"></el-input>
    </el-form-item>
    <el-form-item label="一星评级数" prop="ratingE">
      <el-input v-model="dataForm.ratingE" placeholder="一星评级数"></el-input>
    </el-form-item>
    <el-form-item label="评级人数" prop="ratingCount">
      <el-input v-model="dataForm.ratingCount" placeholder="评级人数"></el-input>
    </el-form-item>
    <el-form-item label="乐观锁版本" prop="version">
      <el-input v-model="dataForm.version" placeholder="乐观锁版本"></el-input>
    </el-form-item>
    <el-form-item label="是否被删除: 0-未删除,1-删除" prop="deleted">
      <el-input v-model="dataForm.deleted" placeholder="是否被删除: 0-未删除,1-删除"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="修改时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="修改时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          subjectId: '',
          subjectName: '',
          ratingScore: '',
          ratingA: '',
          ratingB: '',
          ratingC: '',
          ratingD: '',
          ratingE: '',
          ratingCount: '',
          version: '',
          deleted: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          subjectId: [
            { required: true, message: '影片id不能为空', trigger: 'blur' }
          ],
          subjectName: [
            { required: true, message: '影片名称不能为空', trigger: 'blur' }
          ],
          ratingScore: [
            { required: true, message: '评分不能为空', trigger: 'blur' }
          ],
          ratingA: [
            { required: true, message: '五星评级数不能为空', trigger: 'blur' }
          ],
          ratingB: [
            { required: true, message: '四星评级数不能为空', trigger: 'blur' }
          ],
          ratingC: [
            { required: true, message: '三星评级数不能为空', trigger: 'blur' }
          ],
          ratingD: [
            { required: true, message: '二星评级数不能为空', trigger: 'blur' }
          ],
          ratingE: [
            { required: true, message: '一星评级数不能为空', trigger: 'blur' }
          ],
          ratingCount: [
            { required: true, message: '评级人数不能为空', trigger: 'blur' }
          ],
          version: [
            { required: true, message: '乐观锁版本不能为空', trigger: 'blur' }
          ],
          deleted: [
            { required: true, message: '是否被删除: 0-未删除,1-删除不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/subject/subjectrating/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.subjectId = data.subjectRating.subjectId
                this.dataForm.subjectName = data.subjectRating.subjectName
                this.dataForm.ratingScore = data.subjectRating.ratingScore
                this.dataForm.ratingA = data.subjectRating.ratingA
                this.dataForm.ratingB = data.subjectRating.ratingB
                this.dataForm.ratingC = data.subjectRating.ratingC
                this.dataForm.ratingD = data.subjectRating.ratingD
                this.dataForm.ratingE = data.subjectRating.ratingE
                this.dataForm.ratingCount = data.subjectRating.ratingCount
                this.dataForm.version = data.subjectRating.version
                this.dataForm.deleted = data.subjectRating.deleted
                this.dataForm.createTime = data.subjectRating.createTime
                this.dataForm.updateTime = data.subjectRating.updateTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/subject/subjectrating/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'subjectId': this.dataForm.subjectId,
                'subjectName': this.dataForm.subjectName,
                'ratingScore': this.dataForm.ratingScore,
                'ratingA': this.dataForm.ratingA,
                'ratingB': this.dataForm.ratingB,
                'ratingC': this.dataForm.ratingC,
                'ratingD': this.dataForm.ratingD,
                'ratingE': this.dataForm.ratingE,
                'ratingCount': this.dataForm.ratingCount,
                'version': this.dataForm.version,
                'deleted': this.dataForm.deleted,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
