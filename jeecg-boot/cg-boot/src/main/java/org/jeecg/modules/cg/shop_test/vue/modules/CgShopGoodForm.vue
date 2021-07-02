<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name">
              <j-search-select-tag v-model="model.name" dict="sys_user,realname,username"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="商品价格" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="price">
              <a-input-number v-model="model.price" placeholder="请输入商品价格" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="商品描述" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="content">
              <j-editor v-model="model.content" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="商品类别" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shopTypeId">
  	          <j-tree-select
                ref="treeSelect"
                placeholder="请选择商品类别"
                v-model="model.shopTypeId"
                dict="cg_shop_type,name,id"
                pidValue="0"
                >
              </j-tree-select>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="商品状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status">
              <j-dict-select-tag type="list" v-model="model.status" dictCode="yn" placeholder="请选择商品状态" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="省市区" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="ccc">
             <j-area-linkage type="cascader" v-model="model.ccc" placeholder="请输入省市区"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="用户选择" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="selectName">
              <j-select-depart v-model="model.selectName" multi  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="是否展示" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="isShow">
              <j-switch v-model="model.isShow"  ></j-switch>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="图片" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="pic">
              <j-image-upload isMultiple :number=2 v-model="model.pic" ></j-image-upload>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'CgShopGoodForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
           price: [
              { required: false},
              { pattern: /^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
           ],
        },
        url: {
          add: "/shop_test/cgShopGood/add",
          edit: "/shop_test/cgShopGood/edit",
          queryById: "/shop_test/cgShopGood/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>