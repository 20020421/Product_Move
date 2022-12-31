import { Button, Input, Space, Table } from "antd";
import classNames from "classnames/bind";
import { useEffect, useState } from "react";
import { toast } from "react-hot-toast";
import { get } from "../../utils/request";
import { warrantyReturnToCustomer } from "../../utils/requestJson";
import style from "./DistributorManagerWarrantyDone.module.scss";

const cx = classNames.bind(style);

function DistributorWarrantyDone() {
    const [products, setProducts] = useState([]);

  


    const getProducts = () => {
        get('/api/v1/distributors/warranties/products/done')
            .then(response => {
                setProducts(response)
            })
            .catch(error => console.log(error))
    }

    useEffect(() => {
        getProducts()
    }, [])

    const returnCustomer = (serial) => {
        const data = {
            serial: serial
        }
        toast.promise(warrantyReturnToCustomer(data),{
            loading: 'Processing .....',
            success: 'Done!',
            error: "Error!"
        }).then(response => {
            getProducts()
        })
        console.log(data)
        
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
            <Button  onClick={() => returnCustomer(record.key)} type="primary">Return Customer</Button>
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

export default DistributorWarrantyDone;