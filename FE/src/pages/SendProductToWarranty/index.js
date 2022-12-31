import { Button, Select, Table } from "antd";
import classNames from "classnames/bind";
import { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import { toast } from "react-hot-toast";
import { get } from "../../utils/request";
import { sendErrorToWarranty } from "../../utils/requestJson";
import style from "./SendProductToWarranty.module.scss";

const cx = classNames.bind(style);

function SendProductToWarranty() {


    const [products, setProducts] = useState([]);

    const [show, setShow] = useState(false);

    const [warrantyChoose, setWarrantyChoose] = useState('');

    const handleOpen = () => {
        setShow(true);
    }

    const handleClose = () => {
        setShow(false)
        setSelectedRowsArray([])
    }

    const onSelect = (value, event) => {
        console.log(value)
        setWarrantyChoose(value);
    }


    const getProducts = () => {
        get('/api/v1/distributors/warranties/products')
            .then(response => {
                setProducts(response)
            })
            .catch(error => console.log(error))
    }

    useEffect(() => {
        getProducts()
    }, [])



    
    
    const [selectedRowsArray, setSelectedRowsArray] = useState([]);
    
    const clearSelected = () => {
        setSelectedRowsArray([]);
    }

    const rowSelection = {
        selectedRowKeys: selectedRowsArray,
        onChange: (selectedRowKeys, selectedRows) => {
            setSelectedRowsArray(selectedRowKeys)
        },
        
        getCheckboxProps: (record) => ({
            disabled: record.name === 'Disabled User',
            // Column configuration not to be checked
            name: record.name,
        }),
    };
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
            title: 'Action',
            dataIndex: '',
            key: 'x',
            width: '10%',
            render: (_, record) => <Button type="primary" onClick={() => {
                
                setSelectedRowsArray([record.key]);
                handleOpen()
            }}>Send To Warranty</Button>,
        }
    ];


    const handleSendToWarranty = () => {
        const data = {
            warranty: warrantyChoose,
            serials: selectedRowsArray
        }

        toast.promise(sendErrorToWarranty(data), {
            loading: 'Processing.....',
            success: 'Successfull send ' + selectedRowsArray.length + ' products to ' + warrantyChoose,
            error: 'Fail. Try Again!' 
        }).then(() => {
            setShow(false)
            getProducts()
            clearSelected()
        })

    }


    return (<div className={cx('wrapper')}>
        <Button disabled={selectedRowsArray.length === 0 || show} onClick={handleOpen} style={{ float: 'right', marginBottom: '30px', fontWeight: '600', height: '4.2rem' }} type="primary">Send selected to warranty</Button>
        <Table
            pagination={false}
            rowSelection={{
                type: 'checkbox',
                ...rowSelection,
            }}
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
        <Modal show={show} onHide={handleClose} enforceFocus={false}>
            <Modal.Header closeButton>
                <Modal.Title>Choose Warehouse</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <SelectWarranty onSelect={onSelect} />
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={handleClose}>
                    Close
                </Button>
                <Button disabled={warrantyChoose === ''} onClick={handleSendToWarranty} type="primary">
                    Warehousing
                </Button>
            </Modal.Footer>

        </Modal>
    </div>);
}


function SelectWarranty ({onSelect}) {

    const WARRANTY_CENTER = "Warranty Center"

    const [warrrantyCenters, setWarrantyCenters] = useState([]);

    useEffect(() => {

        get(`/api/v1/branches/${WARRANTY_CENTER}`)
            .then(response => setWarrantyCenters(response))
            .catch(error => console.log(error));

    }, [])


    return ( <Select
        
        
        dropdownStyle={{
            zIndex: '999999'
        }}
        showSearch
        style={{ width: 200 }}
        placeholder="Select a branch"
        optionFilterProp="children"
        onSelect={(value, event) => {
            onSelect(value, event)
        }}
        filterOption={(input, option) =>
            option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
        }
    >
        {
            warrrantyCenters.map((distributor, index) => (
                <Select.Option key={index} value={distributor.name}>{distributor.name}</Select.Option>
            ))
        }
    </Select> );
}


export default SendProductToWarranty;