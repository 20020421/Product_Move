import classNames from "classnames/bind";
import style from "./ProductManagerWarranty.module.scss";




import { Button, Input, Select, Space, Table } from "antd";

import { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import { toast } from "react-hot-toast";
import { get } from "../../utils/request";
import { sendErrorToWarranty, warrantyDone } from "../../utils/requestJson";
import Search from "antd/es/transfer/search";
const cx = classNames.bind(style);

function ProductManagerWarranty() {


    const [products, setProducts] = useState([]);

    const [show, setShow] = useState(false);

    const [warrantyChoose, setWarrantyChoose] = useState('');

    const handleOpen = () => {
        setShow(true);
    }

    const handleClose = () => {
        setShow(false)
    }

    const onSelect = (value, event) => {
        console.log(value)
        setWarrantyChoose(value);
    }


    const getProducts = () => {
        get('/api/v1/warranties/products')
            .then(response => {
                setProducts(response)
            })
            .catch(error => console.log(error))
    }

    useEffect(() => {
        getProducts()
    }, [])

    const handleDoneWarranty = (serial) => {
        const data = {
            serial: serial
        }
        console.log(data)
        toast.promise(warrantyDone(data), {
            loading: 'Processing......',
            success: 'Done !',
            error: 'Error!'
        }).then(response => {
            getProducts();
            console.log(response)
        })
    }




    const columns = [
        {
            title: 'Serial',
            dataIndex: 'serial',
        },
        {
            title: 'Model',
            dataIndex: 'model',
        },
        {
            title: 'Warehouse',
            dataIndex: 'warehouse',
        },
        {
            title: 'Description',
            dataIndex: 'description',
        },
        {
            title: 'Created at',
            dataIndex: 'createdAt'
        },
        {
            title: 'Status',
            dataIndex: 'status'
        },
        {
            title: 'Action',
            dataIndex: '',
            key: 'x',
            width: '10%',
            render: (_, record) => <Space size="middle">
            <Button disabled={record.status === 'Warranty done'} danger type="primary">Send to factory</Button>
            <Button disabled={record.status === 'Warranty done'} onClick={() => handleDoneWarranty(record.key)} type='primary'>Warranty Done</Button>
          </Space>,
        }
    ];

    const [serial, setSerial] = useState();

    const onSearch = () => {
        if (serial !== '') {
            setProducts(products => products.filter(product => product.serial === serial))
        } else {
            getProducts()
        }
    }


    return (<div className={cx('wrapper')}>
        <Input.Group style={{marginBottom: '30px'}} compact>
      <Input
        style={{
          width: 'calc(200px)',
        }}
        placeholder='Input serial'
        onChange={(e) => {
            setSerial(e.target.value)
        }}
      />
      <Button onClick={onSearch}  type="primary">Search</Button>
    </Input.Group>

        <Table
            pagination={false}

            columns={columns}
            dataSource={products.map((product) => {
                return {
                    ...product,
                    key: product.serial,
                    model: product.productModelName,
                    warehouse: product.warehouseName,
                    createdAt: product.updateAt

                }
            })}
        />

    </div>);
}



export default ProductManagerWarranty;