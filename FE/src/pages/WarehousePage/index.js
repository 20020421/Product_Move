import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import classNames from "classnames/bind";
import { useEffect, useState } from "react";
import { Button, Modal} from "react-bootstrap";
import FormAddWarehouse from "../../components/FormAddWarehouse";
import { get } from "../../utils/request";
import style from "./WarehousePage.module.scss";
import Pagination from "react-pagination-library"
import "react-pagination-library/build/css/index.css";
import { addWarehouse } from "../../utils/requestJson";

const cx = classNames.bind(style);

function WarehousePage() {

    const [pageState, setPageState] = useState({
        currentPage: 1,
        totalPages: 10
    })

    const changeCurrentPage = numPage => {
        setPageState({
            ...pageState,
            currentPage: numPage
        });
    }

    const [newWarehouse, setNewWarehouse] = useState({
        name: '',
        address: ''
    })



    const [warehouses, setWarehouses] = useState([]);

    useEffect(() => {
        get(`./api/v1/warehouses?page=${pageState.currentPage - 1}&size=5`)
        .then(response => {
            setWarehouses(response.warehouses)
            setPageState({
                ...pageState,
                totalPages: response.totalPages
            })
            console.log(response);
        })
        .catch(error => console.log(error))
    }, [pageState.currentPage])
    const[show, setShow] = useState(false);
    const handleOpen = () => {
        setShow(true);
    }

    const handleClose = () => {
        setShow(false);
    }

    const handleAddNewWarehouse = () => {
        if (newWarehouse.name !== '' && newWarehouse.address !== '') {
            addWarehouse(newWarehouse)
            .then(response => {
                warehouses.push(response.data)
                handleClose();
            })
            .catch(error => console.log(error))

        }
    }

    return ( <div className={cx('wrapper')}>
        <div className={cx('header')}>
            <h1 className={cx('warehouse-header')}>
                WareHouse
            </h1>
            <div className={cx('new-warehouse')}>
            <button className={cx('btn-create')} onClick={handleOpen}>
                <FontAwesomeIcon icon={faPlus} className={cx('icon')} />
                <span>New Warehouse</span>
            </button>
        </div>
        </div>
        <div className={cx('table')}>
            <table className={cx('models')}>
                <thead>
                    <tr className={cx('header-tb')}>
                        <th>

                        </th>

                        <th>
                            WareHouse
                        </th>
                        <th>
                            Address
                        </th>
                        <th>
                            Total Product
                        </th>
                        <th>
                            Actions
                        </th>
                    </tr>

                </thead>
                <tbody>
                    {
                        warehouses.map((warehouse, index) => (
                            <tr key={index}>
                                <td></td>
                                <td>{warehouse.name}</td>
                                <td>{warehouse.address}</td>
                                <td>{warehouse.totalProduct}</td>
                                <td></td>
                            </tr>
                        ))
                        
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
        <Modal show={show} onHide={handleClose} enforceFocus={false}>
            <Modal.Header closeButton>
                <Modal.Title>New WareHouse</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <FormAddWarehouse state={newWarehouse} setState={setNewWarehouse} />
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Close
                </Button>
                <Button  onClick={handleAddNewWarehouse}  variant="primary">
                    Create
                </Button>
            </Modal.Footer>

        </Modal>
    </div> );
}

export default WarehousePage;