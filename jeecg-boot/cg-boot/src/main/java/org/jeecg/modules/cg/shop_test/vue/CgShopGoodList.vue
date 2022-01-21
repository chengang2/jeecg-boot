<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品名称">
              <j-search-select-tag placeholder="请选择商品名称" v-model="queryParam.name" dict="sys_user,realname,username"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品类别">
              <a-input placeholder="请输入商品类别" v-model="queryParam.shopTypeId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('测试商品类别')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="pcaSlot" slot-scope="text">
          <div>{{ getPcaText(text) }}</div>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <cg-shop-good-modal ref="modalForm" @ok="modalFormOk"></cg-shop-good-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import CgShopGoodModal from './modules/CgShopGoodModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import Area from '@/components/_util/Area'

  export default {
    name: 'CgShopGoodList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      CgShopGoodModal
    },
    data () {
      return {
        description: '测试商品类别管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'商品名称',
            align:"center",
            dataIndex: 'name_dictText'
          },
          {
            title:'商品价格',
            align:"center",
            dataIndex: 'price'
          },
          {
            title:'商品描述',
            align:"center",
            dataIndex: 'content',
            scopedSlots: {customRender: 'htmlSlot'}
          },
          {
            title:'商品类别',
            align:"center",
            dataIndex: 'shopTypeId'
          },
          {
            title:'商品状态',
            align:"center",
            dataIndex: 'status_dictText'
          },
          {
            title:'省市区',
            align:"center",
            dataIndex: 'ccc',
            scopedSlots: {customRender: 'pcaSlot'}
          },
          {
            title:'用户选择',
            align:"center",
            dataIndex: 'selectName_dictText'
          },
          {
            title:'是否展示',
            align:"center",
            dataIndex: 'isShow',
            customRender: (text) => (text ? filterMultiDictText(this.dictOptions['isShow'], text) : ''),
          },
          {
            title:'图片',
            align:"center",
            dataIndex: 'pic',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/shop_test/cgShopGood/list",
          delete: "/shop_test/cgShopGood/delete",
          deleteBatch: "/shop_test/cgShopGood/deleteBatch",
          exportXlsUrl: "/shop_test/cgShopGood/exportXls",
          importExcelUrl: "shop_test/cgShopGood/importExcel",
          
        },
        dictOptions:{},
        pcaData:'',
        superFieldList:[],
      }
    },
    created() {
      this.pcaData = new Area()
      this.$set(this.dictOptions, 'isShow', [{text:'是',value:'Y'},{text:'否',value:'N'}])
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      getPcaText(code){
        return this.pcaData.getText(code);
      },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'sel_search',value:'name',text:'商品名称',dictTable:'sys_user', dictText:'realname', dictCode:'username'})
        fieldList.push({type:'BigDecimal',value:'price',text:'商品价格',dictCode:''})
        fieldList.push({type:'Text',value:'content',text:'商品描述',dictCode:''})
        fieldList.push({type:'string',value:'shopTypeId',text:'商品类别'})
        fieldList.push({type:'int',value:'status',text:'商品状态',dictCode:'yn'})
        fieldList.push({type:'pca',value:'ccc',text:'省市区'})
        fieldList.push({type:'sel_depart',value:'selectName',text:'用户选择'})
        fieldList.push({type:'switch',value:'isShow',text:'是否展示'})
        fieldList.push({type:'string',value:'pic',text:'图片',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>