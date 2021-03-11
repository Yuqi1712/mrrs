<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="中文名" prop="nameCn">
      <el-input v-model="dataForm.nameCn" placeholder="中文名"></el-input>
    </el-form-item>
    <el-form-item label="英文名" prop="nameUs">
      <el-input v-model="dataForm.nameUs" placeholder="英文名"></el-input>
    </el-form-item>
    <el-form-item label="MAN-男，WOMAN-女" prop="sex">
      <el-input v-model="dataForm.sex" placeholder="MAN-男，WOMAN-女"></el-input>
    </el-form-item>
    <el-form-item label="星座" prop="constellation">
      <el-input v-model="dataForm.constellation" placeholder="星座"></el-input>
    </el-form-item>
    <el-form-item label="出生日期" prop="birthday">
      <el-input v-model="dataForm.birthday" placeholder="出生日期"></el-input>
    </el-form-item>
    <el-form-item label="出生地" prop="birthPlace">
      <el-input v-model="dataForm.birthPlace" placeholder="出生地"></el-input>
    </el-form-item>
    <el-form-item label="职业" prop="profession">
      <el-input v-model="dataForm.profession" placeholder="职业"></el-input>
    </el-form-item>
    <el-form-item label="别名" prop="otherName">
      <el-input v-model="dataForm.otherName" placeholder="别名"></el-input>
    </el-form-item>
    <el-form-item label="名人介绍" prop="introduction">
      <el-input v-model="dataForm.introduction" placeholder="名人介绍"></el-input>
    </el-form-item>
    <el-form-item label="展示图片" prop="playImg">
      <el-input v-model="dataForm.playImg" placeholder="展示图片"></el-input>
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
          nameCn: '',
          nameUs: '',
          sex: '',
          constellation: '',
          birthday: '',
          birthPlace: '',
          profession: '',
          otherName: '',
          introduction: '',
          playImg: '',
          version: '',
          deleted: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          nameCn: [
            { required: true, message: '中文名不能为空', trigger: 'blur' }
          ],
          nameUs: [
            { required: true, message: '英文名不能为空', trigger: 'blur' }
          ],
          sex: [
            { required: true, message: 'MAN-男，WOMAN-女不能为空', trigger: 'blur' }
          ],
          constellation: [
            { required: true, message: '星座不能为空', trigger: 'blur' }
          ],
          birthday: [
            { required: true, message: '出生日期不能为空', trigger: 'blur' }
          ],
          birthPlace: [
            { required: true, message: '出生地不能为空', trigger: 'blur' }
          ],
          profession: [
            { required: true, message: '职业不能为空', trigger: 'blur' }
          ],
          otherName: [
            { required: true, message: '别名不能为空', trigger: 'blur' }
          ],
          introduction: [
            { required: true, message: '名人介绍不能为空', trigger: 'blur' }
          ],
          playImg: [
            { required: true, message: '展示图片不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/celebrity/celebrity/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.nameCn = data.celebrity.nameCn
                this.dataForm.nameUs = data.celebrity.nameUs
                this.dataForm.sex = data.celebrity.sex
                this.dataForm.constellation = data.celebrity.constellation
                this.dataForm.birthday = data.celebrity.birthday
                this.dataForm.birthPlace = data.celebrity.birthPlace
                this.dataForm.profession = data.celebrity.profession
                this.dataForm.otherName = data.celebrity.otherName
                this.dataForm.introduction = data.celebrity.introduction
                this.dataForm.playImg = data.celebrity.playImg
                this.dataForm.version = data.celebrity.version
                this.dataForm.deleted = data.celebrity.deleted
                this.dataForm.createTime = data.celebrity.createTime
                this.dataForm.updateTime = data.celebrity.updateTime
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
              url: this.$http.adornUrl(`/celebrity/celebrity/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'nameCn': this.dataForm.nameCn,
                'nameUs': this.dataForm.nameUs,
                'sex': this.dataForm.sex,
                'constellation': this.dataForm.constellation,
                'birthday': this.dataForm.birthday,
                'birthPlace': this.dataForm.birthPlace,
                'profession': this.dataForm.profession,
                'otherName': this.dataForm.otherName,
                'introduction': this.dataForm.introduction,
                'playImg': this.dataForm.playImg,
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
