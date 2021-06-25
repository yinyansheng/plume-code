<template>
  <d2-container>
    <template slot="header"><d2-icon name="diamond"/> 代码生成</template>
    <el-row>
      <el-col :span="4">
        <el-select style="width: 100%;" v-model="value" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <div style="padding:10px">
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
          <div style="margin: 15px 0;"></div>
          <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
            <el-checkbox style="display: block" v-for="city in cities" :label="city" :key="city">{{city}}</el-checkbox>
          </el-checkbox-group>
        </div>
      </el-col>
      <el-col :span="20">
        <el-form :model="ruleForm"  ref="ruleForm" label-width="100px" class="demo-ruleForm">

          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>项目信息</span>
            </div>
            <div>
              <el-form-item label="项目名称" prop="desc">
                <el-input  v-model="ruleForm.desc" placeholder="项目名：plume_code"></el-input>
              </el-form-item>
              <el-form-item label="包名" prop="desc">
                <el-input  v-model="ruleForm.desc" placeholder="报名：com.github.plume"></el-input>
              </el-form-item>
            </div>
          </el-card>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>项目信息</span>
            </div>
            <div>
          <el-form-item label="页面层" prop="name">
            <el-tooltip  style="margin-right: 10px" effect="dark" content="生成api.js,CURD.vue结构" placement="top">
              <i class="el-icon-warning-outline"></i>
            </el-tooltip>
            <el-checkbox><img src="https://cdn.d2.pub/files/image-hosting/20200215125724.png" width="20px" height="20px" /> <a href="https://d2.pub/zh/"  target="_blank">d2admin</a></el-checkbox>
            <el-checkbox><img src="https://file.iviewui.com/file/iview-design-favicon.ico" width="20px" height="20px" /> <a href="https://www.iviewui.com/"  target="_blank">iview</a></el-checkbox>
          </el-form-item>
          <el-form-item label="控制层" prop="region">
            <el-tooltip  style="margin-right: 10px" effect="dark" content="生成controller类" placement="top">
              <i class="el-icon-warning-outline"></i>
            </el-tooltip>
            <el-checkbox>api</el-checkbox>
            <el-checkbox>admin</el-checkbox>
          </el-form-item>
          <el-form-item label="服务层">
            <el-tooltip  style="margin-right: 10px" effect="dark" content="生成service接口与service实现， DTO类" placement="top">
              <i class="el-icon-warning-outline"></i>
            </el-tooltip>
            <el-checkbox>service</el-checkbox>
            <el-checkbox>dto</el-checkbox>
          </el-form-item>
          <el-form-item label="持久层" prop="delivery">
            <el-tooltip  style="margin-right: 10px" effect="dark" content="生成Mapper接口与表对应的实体类" placement="top">
              <i class="el-icon-warning-outline"></i>
            </el-tooltip>
            <el-checkbox><img src="data:image/jpeg;base64,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" height="20px"  width="20px"/> <a href="https://blog.mybatis.org/" target="_blank">mybatis</a></el-checkbox>
            <el-checkbox><img src="https://mybatis.plus/favicon.ico"  height="20px" width="20px"/> <a href="https://mybatis.plus/" target="_blank" >mybatis plus</a></el-checkbox>
            <el-checkbox><img src="https://img.icons8.com/color/452/spring-logo.png" width="20px" height="20px" /> <a href="https://spring.io/projects/spring-data-jpa" target="_blank">jpa</a></el-checkbox>
          </el-form-item>
            </div>
          </el-card>
          <el-card class="box-card">
            <el-collapse v-model="open" accordion>
              <el-collapse-item title="高级配置" name="1">
                <el-form-item label="作者：" prop="desc">
                  <el-input  v-model="ruleForm.desc" placeholder="作者"></el-input>
                </el-form-item>
                <el-form-item label="" prop="name">
                  <el-checkbox><img src="https://static1.smartbear.co/swagger/media/assets/swagger_fav.png" width="20px" height="20px" /> <a href="https://swagger.io/"  target="_blank">swagger</a></el-checkbox>
                  <el-checkbox><img src="https://avatars.githubusercontent.com/u/45949248?s=200&v=4"  width="20px" height="20px" /> <a href="https://www.iviewui.com/"  target="_blank">lombok</a></el-checkbox>
                </el-form-item>
                <el-form-item label="表名前缀：" prop="desc">
                  <el-input  v-model="ruleForm.desc" placeholder="例如：plume_"></el-input>
                </el-form-item>
                <el-form-item label="字段前缀：" prop="desc">
                  <el-input  v-model="ruleForm.desc" placeholder="例如：n_,s_,d_"></el-input>
                </el-form-item>
              </el-collapse-item>
            </el-collapse>
          </el-card>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">一键下载</el-button>
            <el-button @click="resetForm('ruleForm')">查看</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </d2-container>
</template>

<script>
const cityOptions = ['上海', '北京', '广州', '深圳'];
export default {
  name: 'generator',

  data() {
    return {
      open: "0",
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      value: '',
      checkAll: false,
      checkedCities: ['上海', '北京'],
      cities: cityOptions,
      isIndeterminate: true,
      ruleForm: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        region: [
          { required: true, message: '请选择活动区域', trigger: 'change' }
        ],
        date1: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        date2: [
          { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
        ],
        type: [
          { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
        ],
        resource: [
          { required: true, message: '请选择活动资源', trigger: 'change' }
        ],
        desc: [
          { required: true, message: '请填写活动形式', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    handleCheckAllChange(val) {
      this.checkedCities = val ? cityOptions : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cities.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!');
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>
<style lang="scss" scoped>
 .box-card {
   margin-bottom: 10px;
 }
</style>
