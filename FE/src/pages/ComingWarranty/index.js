import { Button, Select, Table } from "antd";
import classNames from "classnames/bind";
import { useEffect, useState } from "react";
import { get } from "../../utils/request";
import { ColorProduct } from "../ProductPage";
import style from "./ComingWarranty.module.scss";
import {ImportOutlined } from '@ant-design/icons';
import { Modal } from "react-bootstrap";
import { toast } from "react-hot-toast";
import { distributorWarehousingAll, warrantyWarehousing } from "../../utils/requestJson";


const cx = classNames.bind(style);
function ComingWarranty() {
const columns = [
    {
      title: 'From Distributor',
      dataIndex: 'distributor',
      key: 'distributor',
    },
    {
      title: 'Quantity',
      dataIndex: 'quantity',
      key: 'quantity',
    },
    {
      title: 'Created Date',
      dataIndex: 'date',
      key: 'date',
    },
    {
      title: 'Action',
      dataIndex: '',
      key: 'x', 
      width: '10%',
      render: (_, record) => <Button type="primary"  onClick={() => handleOpen(1, record.key)}>Warehousing</Button>,
    },
  ];

  const columnPro = [
    {
        title: 'Model',
        dataIndex: 'productModelName',
        key: 'productModelName',
    },
    {
        title: 'Color',
        dataIndex: 'colorString',
        key: 'colorString',
        render: (_, record) => {
            return <ColorProduct style={{height: '30px', width: '30px', border: '2px solid #3c4b64', borderRadius: '5px'}} colorName={record.colorString}/>}
    }, 
    {
        title: 'Capacity',
        dataIndex: 'capacityInt',
        key: 'capacityInt',
        render: (_, record) => {
          const text = record.capacityInt >= 1024 ? record.capacityInt / 1024 + 'TB' : record.capacityInt + "GB";
          return <span>{text}</span>
        }
    },
    {
      title: 'Serial',
      dataIndex: 'serial',
      key: 'serial',
      
  },
  {
    title: 'Description',
    dataIndex: 'description',
    key: 'description',
    
  }

  ]



    const [data, setData] = useState([])
    const[show, setShow] = useState(false);
    const [typeWarehousing, setTypeWarehousing] = useState();
    const [distributorChoose, setDistributorChoose]  = useState('')

    const handleOpen = (type,distributor, event) => {
      setTypeWarehousing(type)
      if (distributor) {
        setDistributorChoose(distributor);
        // console.log(factory)
      }
        setShow(true);
    }


    const resetData = () => {
      handleClose()
      Promise.all([get('/api/v1/distributors/warehousing'), get(`/api/v1/warehouses?page=0&size=1000`)])
      .then(response => {
        const arr = []
            Object.entries(response[0]).forEach((coming, index) => {
                arr.push({
                    key: coming[0],
                    factory: coming[0],
                    date: coming[1][0].updateAt,
                    quantity: coming[1].length,
                    products: coming[1]
                })
            })

            setWarehouses(response[1].warehouses)

            setData(arr)
      })
    }

    const [warehouseChooes, setWarehouseChooes] = useState('')

    const handleWarehousing = () => {

      let dataPost = {
        type: typeWarehousing,
        warehouse: warehouseChooes,
      }
      if (typeWarehousing === 1) {
        dataPost = {
          ...dataPost,
          distributor: distributorChoose
        }
      }

      console.log(dataPost)

      toast.promise(warrantyWarehousing(dataPost), {
              loading: 'Processing....',
              success: 'Warehousing Successfully',
              error: 'Error to warehousing',
          }).then(response => resetData());

    }

    const handleClose = () => {
        setTypeWarehousing(null)
        setShow(false);
        setDistributorChoose('')
    }


    const [warehouses, setWarehouses] = useState([]);



    useEffect(() => {

      Promise.all([get('/api/v1/warranties/products/coming'), get(`./api/v1/warehouses?page=0&size=1000`)])
      .then(response => {
        const arr = []
            Object.entries(response[0]).forEach((coming, index) => {
                arr.push({
                    key: coming[0],
                    distributor: coming[0],
                    date: coming[1][0].updateAt,
                    quantity: coming[1].length,
                    products: coming[1]
                })
            })

            setWarehouses(response[1].warehouses)

            setData(arr)
      })
    } , [])

    return ( <div className={cx('wrapper')}>
      <div className={cx('warehousing-all')}>
        <Button disabled={data.length === 0} type="primary" icon={<ImportOutlined />} style={{float: 'right', display: 'flex', padding: '20px 30px', justifyContent: 'center', alignItems: 'center', fontSize: '1.5rem'}} onClick={() => handleOpen(0)}>Warehousing all</Button>
      </div>
        <Table
    columns={columns}
    expandable={{
      expandedRowRender: (record) => {
        
        return (
        <Table
            pagination={false}
          style={{
            margin: 0,
          }}
          columns={columnPro}
          dataSource={record.products.map(product => {
            return {
              ...product,

              key: product.id
            }
          })} 
         />
      )},
      rowExpandable: (record) => record.name !== 'Not Expandable',
    }}
    dataSource={data}
    pagination={false}
  />
   <Modal show={show} onHide={handleClose} enforceFocus={false}>
            <Modal.Header closeButton>
                <Modal.Title>Choose Warehouse</Modal.Title>
            </Modal.Header>
            <Modal.Body>

     <Select
        
        
        dropdownStyle={{
            zIndex: '999999'
        }}
        showSearch
        style={{ width: 200 }}
        placeholder="Select a warehouse"
        optionFilterProp="children"
        onSelect={(value, event) => {
            setWarehouseChooes(value)
        }}
        filterOption={(input, option) =>
            option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
        }
    >
        {
            warehouses.map((warehouse, index) => (
                <Select.Option key={index} value={warehouse.name}>{warehouse.name}</Select.Option>
            ))
        }
    </Select> 
            </Modal.Body>
            <Modal.Footer>
                <Button  onClick={handleClose}>
                    Close
                </Button>
                <Button onClick={handleWarehousing} disabled={warehouseChooes === ''}  type="primary">
                    Warehousing
                </Button>
            </Modal.Footer>

        </Modal>
    </div> );
}

export default ComingWarranty;