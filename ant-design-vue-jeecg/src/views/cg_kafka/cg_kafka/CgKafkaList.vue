<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="脚本名">
              <a-input placeholder="请输入脚本名" v-model="queryParam.shellName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="kafka集群">
              <j-popup placeholder="请选择kafka集群" v-model="queryParam.kafkaClusterName" code="cg_kc" org-fields="kafka_cluster_name,broker_services" dest-fields="kafka_cluster_name,kafka_broker_service" :field="getPopupField('kafka_cluster_name,kafka_broker_service')"/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="topic名">
                <a-input placeholder="请输入topic名" v-model="queryParam.topicName"></a-input>
              </a-form-item>
            </a-col>
          </template>
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
      <a-button type="primary" icon="download" @click="handleExportXls('kafka同步')">导出</a-button>
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
                <a @click="handleKafkaStart(record)">启动</a>
              </a-menu-item>
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

    <cg-kafka-modal ref="modalForm" @ok="modalFormOk"></cg-kafka-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import CgKafkaModal from './modules/CgKafkaModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'CgKafkaList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      CgKafkaModal
    },
    data () {
      return {
        description: 'kafka同步管理页面',
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
            title:'脚本名',
            align:"center",
            dataIndex: 'shellName'
          },
          {
            title:'kafka集群',
            align:"center",
            dataIndex: 'kafkaClusterName'
          },
          {
            title:'kafka连接信息',
            align:"center",
            dataIndex: 'kafkaBrokerService'
          },
          {
            title:'topic名',
            align:"center",
            dataIndex: 'topicName'
          },
          {
            title:'分区数',
            align:"center",
            dataIndex: 'topicPartition'
          },
          {
            title:'内存大小',
            align:"center",
            dataIndex: 'useMem_dictText'
          },
          {
            title:'运行主机',
            align:"center",
            dataIndex: 'kafkaNode_dictText'
          },
          {
            title:'业务负责人',
            align:"center",
            dataIndex: 'userBy'
          },
          {
            title:'业务用途',
            align:"center",
            dataIndex: 'userFor'
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
          list: "/cg_kafka/cgKafka/list",
          delete: "/cg_kafka/cgKafka/delete",
          deleteBatch: "/cg_kafka/cgKafka/deleteBatch",
          exportXlsUrl: "/cg_kafka/cgKafka/exportXls",
          importExcelUrl: "cg_kafka/cgKafka/importExcel",
          start: "/cg_kafka/cgKafka/start",
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'shellName',text:'脚本名',dictCode:''})
        fieldList.push({type:'popup',value:'kafkaClusterName',text:'kafka集群', popup:{code:'cg_kc',field:'kafka_cluster_name',orgFields:'kafka_cluster_name',destFields:'kafka_cluster_name'}})
        fieldList.push({type:'string',value:'kafkaBrokerService',text:'kafka连接信息',dictCode:''})
        fieldList.push({type:'string',value:'topicName',text:'topic名',dictCode:''})
        fieldList.push({type:'int',value:'topicPartition',text:'分区数',dictCode:''})
        fieldList.push({type:'string',value:'useMem',text:'内存大小',dictCode:'use_mem'})
        fieldList.push({type:'list_multi',value:'kafkaNode',text:'运行主机',dictTable:'', dictText:'', dictCode:'kafka_node'})
        fieldList.push({type:'string',value:'userBy',text:'业务负责人',dictCode:''})
        fieldList.push({type:'string',value:'userFor',text:'业务用途',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>