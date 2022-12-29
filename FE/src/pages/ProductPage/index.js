
import 'bootstrap/dist/css/bootstrap.min.css';

import classNames from "classnames/bind";
import { useContext, useEffect, useState } from "react";
import style from "./ProductPage.module.scss";

import Pagination from "react-pagination-library";
import "react-pagination-library/build/css/index.css";
import { optionNumber } from "../../utils/optionValue";
import { get } from "../../utils/request";
import { Modal, OverlayTrigger, Tooltip } from "react-bootstrap";
import Checkbox from "antd/es/checkbox/Checkbox";
import { Button, Select } from "antd";
import { PoweroffOutlined , LogoutOutlined } from '@ant-design/icons';
import { AuthContext } from "../../context/UserContext";
import { exportProducts } from '../../utils/requestJson';
import { toast } from 'react-hot-toast';

const cx = classNames.bind(style);
const defaultCheckedList = []
function ProductPage() {

    const [show, setShow] = useState(false);
    const handleOpen = () => setShow(true);
    const handleClose = () => {
        setDistributorChoose('')
        setShow(false)
    };

    const [pageState, setPageState] = useState({
        currentPage: 1,
        totalPages: 10
    })

    const [filterState, setFilterState] = useState({
        quantity: optionNumber[0].value,
        model: '',
        capacity: '',
        createdAt: '',
        warehouse: '',
        status: '',

    })

    const [distributorChoose, setDistributorChoose] = useState('');

    const onSelect = (value, event) => {
        setDistributorChoose(value)
    }


    const handleExport = () => {
        const data = {
            distributorName: distributorChoose,
            productsSerial: checkedList
        };

        toast.promise(exportProducts(data), {
            loading: 'Processing....',
            success: 'Export Success ' + checkedList.length + ' products',
            error: 'Error to export',
        });

        
    }

    const [checkedList, setCheckedList] = useState(defaultCheckedList);
  const [indeterminate, setIndeterminate] = useState(false);
  const [checkAll, setCheckAll] = useState(false);
  const [products, setProducts] = useState([]);
  const [userState, dispatch] = useContext(AuthContext);

  useEffect(() => {
    if (checkedList.length === products.length) {
        setIndeterminate(false)
    } else if (checkedList.length > 0) {
        setIndeterminate(true)
    }
  }, [checkedList])


  const onCheckAllChange = (e) => {

    let arr = e.target.checked ? products.map((product) => { 
        if (product.status !== "Coming Distribution") {
            return product.serial;
        } 
        return;
        }) : []
    arr = arr.filter(item => item !== undefined);
    setCheckedList(arr);
    setIndeterminate(false);
    setCheckAll(e.target.checked);
  };


    useEffect(() => {
        setCheckedList([]);
    setIndeterminate(false);
    setCheckAll(false);
        get(`/api/v1/branches/products?page=${pageState.currentPage - 1}&size=${filterState.quantity}`)
        .then(response => {
            setPageState({
                ...pageState,
                totalPages: response.totalPages
            })
            setProducts(response.products)
            })
        .catch(error => console.log(error))
    }, [filterState.quantity, pageState.currentPage])

    const changeCurrentPage = numPage => {
        setPageState({
            ...pageState,
            currentPage: numPage
        });
    }

    const convertCapacity = (capacity) => {
        const text = capacity >= 1024 ? capacity / 1024 + "TB" : capacity + "GB";
        return text;
    }
    return ( <div className={cx('wrapper')}>
    <div className={cx('header')}>
        <h1 className={cx('model-header')}>
            Products
        </h1>
        <div className={cx('action-options')}>
            {
                userState.userInfo.role === 'FACTORY' &&
                <Button
                style={{padding: '20px 30px', display: 'flex', justifyContent: 'center', alignItems: 'center', fontSize: '1.6rem'}}
                type="primary"
                icon={<LogoutOutlined />}
                loading={false}
                disabled={checkedList.length === 0}
                onClick={handleOpen}
                >
                Send to distributor agent
                </Button>
            }    
                

            
        </div>
        
    </div>
    <div className={cx('table')}>
        <table className={cx('models')}>
            <thead>
                <tr className={cx('header-tb')}>
                    <th>
                    <select value={filterState.quantity} onChange={(e) => {
                                setPageState({
                                    ...pageState,
                                    currentPage: 1
                                })
                                setFilterState({
                                    ...filterState,
                                    quantity: e.target.value
                                });
                            }}>
                                {
                                    optionNumber.map((option, index) => (
                                        <option key={index} value={option.value}>
                                            {option.value}
                                        </option>
                                    ))
                                }
                            </select>
                    </th>

                    <th>
                        Model
                    </th>
                    <th>
                        Color
                    </th>
                    <th>
                        Capacity
                    </th>
                    <th>
                        Date of Manufacture
                    </th>
                    <th>
                        Status
                    </th>
                    <th>
                        Serial
                    </th>
                    <th>
                        Warehouse
                    </th>
                    <th>
                        Actions
                    </th>
                </tr>
                <tr className={cx('filter')}>
                        <th>
                        <Checkbox indeterminate={indeterminate} onChange={onCheckAllChange} checked={checkAll} />
                        </th>
                        <th>
                            <input value={filterState.model} onChange={(e) => setFilterState({
                                ...filterState,
                                model: e.target.value
                            })} autoComplete='off' type="text" name="model" spellCheck={false} className={cx('filter-field')} />
                        </th>
                        <th>
                            &nbsp;
                        </th>
                        <th>
                            <input value={filterState.capacity} onChange={(e) => setFilterState({
                                ...filterState,
                                capacity: e.target.value
                            })} type="text" name="capacity" spellCheck={false} className={cx('filter-field')} />
                        </th>
                            <th>
                                <input value={filterState.createdAt} onChange={(e) => setFilterState({
                                    ...filterState,
                                    createdAt: e.target.value
                                })} type="date" name="Created at" spellCheck={false} className={cx('filter-field')} />
                            </th>
                        <th>
                            <input value={filterState.status} onChange={(e) => setFilterState({
                                ...filterState,
                                status: e.target.value
                            })} type="text" name="status" spellCheck={false} className={cx('filter-field')} />
                        </th>
                        <th>
                            &nbsp;
                        </th>
                        <th>
                            <input value={filterState.warehouse} onChange={(e) => setFilterState({
                                ...filterState,
                                warehouse: e.target.value
                            })} type="text" name="warehouse" spellCheck={false} className={cx('filter-field')} />
                        </th>
                    </tr>

            </thead>
            <tbody>
                {
                   products.map( (product, index) => {
                    return (
                    <tr key={index}>
                        <td><Checkbox disabled={product.status === 'Coming Distribution'}  checked={checkedList.includes(product.serial)} onClick={() => {
                            
                            const index = checkedList.indexOf(product.serial);
                            if (index > -1) {
                                setCheckedList(checkedList => checkedList.filter((serial, index) => serial !== product.serial))
                            } else {
                                setCheckedList([...checkedList, product.serial])
                            }
                        }} /></td>
                        <td>{product.productModelName}</td>
                        <td><ColorProduct colorName={product.colorString} /></td>
                        <td>{convertCapacity(product.capacityInt)}</td>
                        <td>{product.createdAt}</td>
                        <td>{product.status}</td>
                        <td>{product.serial}</td>
                        <td>{product.warehouseName}</td>
                    </tr>
                   )})
                }

            </tbody>

        </table>
        <Pagination
            currentPage={pageState.currentPage}
            totalPages={pageState.totalPages}
            changeCurrentPage={changeCurrentPage}
            theme="square-i"
        />

    </div>
    {
        userState.userInfo.role === 'FACTORY' &&
    <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Choose Distributor Agent</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <SelectDistributorAgent onSelect={onSelect} />
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={handleClose}>
                    Close
                </Button>
                <Button onClick={handleExport} disabled={distributorChoose === ''} type="primary">
                    Submit
                </Button>
            </Modal.Footer>

        </Modal>
    }
  
</div> );
}

function SelectDistributorAgent ({onSelect}) {

    const DISTRIBUTOR_AGENT = "Distributor Agent"

    const [distributors, setDistributor] = useState([]);

    useEffect(() => {

        get(`/api/v1/branches/${DISTRIBUTOR_AGENT}`)
            .then(response => setDistributor(response))
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
            distributors.map((distributor, index) => (
                <Select.Option key={index} value={distributor.name}>{distributor.name}</Select.Option>
            ))
        }
    </Select> );
}


export const  ColorProduct = ({colorName, style}) =>  {


    const [color, setColors] = useState();
    useEffect(() => {
        if (colorName !== '') {
            get(`/api/v1/products/colors?color=${colorName}`)
            .then(response => setColors(Object.entries(response)))
            .catch(error => console.log(error))
        }
    }, [colorName])

    const myStyle = {...style, backgroundColor: `${color ? color[0][1] : ""}`}

    return (<OverlayTrigger
        placement='top'
        overlay={
            <Tooltip >
                {color && color[0][0]}
            </Tooltip>
        }
    >
        <div style={color && myStyle} className={cx('color-single')} />
    </OverlayTrigger> );
}



export default ProductPage;