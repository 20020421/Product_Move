import 'bootstrap/dist/css/bootstrap.min.css';
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import classNames from "classnames/bind";
import { useEffect, useState } from "react";
import { Button, Modal } from "react-bootstrap";
import { optionNumber } from "../../utils/optionValue";
import { get } from "../../utils/request";
import style from "./UserPage.module.scss";
import FormNewAccount from '../../components/FormNewAccount';
import "./CustomModalStyle.scss";
import { getFormData } from '../../utils/FormData';
import axios from 'axios';
// import Pagination
import Pagination from "react-pagination-library"
import "react-pagination-library/build/css/index.css";
import "./PaginationStyle.scss";


const cx = classNames.bind(style);


function UserPage() {

    const [accounts, setAccounts] = useState([]);

    const [newAccount, setNewAccount] = useState({
        username: '',
        password: '',
        avatarFile: File,
        branchName: ''
    })

    const [show, setShow] = useState(false);

    const [pageState, setPageState] = useState({
        currentPage: 1,
        totalPages: 0
    })

    const handleClose = () => {
        setNewAccount({
            username: '',
            password: '',
            avatarFile: File,
            branchName: ''
        })
        setShow(false)
    };

    const changeCurrentPage = numPage => {
        setPageState({
            ...pageState,
            currentPage: numPage
        });
    }

    const handleCreateAccount = () => {
        // console.log(newAccount)
        const formData = getFormData(newAccount);
        const config = {
            method: 'POST',
            url: 'http://localhost:8080/api/v1/users',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('access_token')
            },
            data: formData
        }
        axios(config)
            .then(response => {
                get(`/api/v1/users?page=${pageState.currentPage - 1}&size=${filterState.quantity}`)
                    .then(response => {
                        setAccounts(response.users)
                        setPageState({
                            ...pageState,
                            totalPages: response.totalPages
                        })
                    })
                    .catch(error => console.log(error));
                handleClose();
            })
            .catch(error => console.log(error));
    }
    const handleOpen = () => setShow(true);

    const [filterState, setFilterState] = useState({
        quantity: optionNumber[0].value,
        username: '',
        role: '',
        branchName: '',
        createdAt: '',
    })
    useEffect(() => {
        get(`/api/v1/users?page=${pageState.currentPage - 1}&size=${filterState.quantity}`)
            .then(response => {
                console.log(response)
                setAccounts(response.users)
                setPageState({
                    ...pageState,
                    totalPages: response.totalPages
                })
            })
            .catch(error => console.log(error));
    // eslint-disable-next-line
    }, [pageState.currentPage, filterState.quantity])
    // const [quantity, setQuantity] = useState(optionNumber[0].value);

    return (<div className={cx('wrapper')}>
        <div className={cx('header')}>
            <h1 className={cx('account-header')}>
                Accounts
            </h1>
            <div className={cx('new-account')}>
                <button className={cx('btn-create')} onClick={handleOpen}>
                    <FontAwesomeIcon icon={faPlus} className={cx('icon')} />
                    <span>New Account</span>
                </button>
            </div>
        </div>
        <div className={cx('table')}>
            <table className={cx('accounts')}>
                <thead>
                    <tr className={cx('header-tb')}>
                        <th className={cx('c0')}>
                            <select value={filterState.quantity} onChange={(e) => {
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
                            Username
                        </th>
                        <th>
                            Role
                        </th>
                        <th>
                            Branch name
                        </th>
                        <th>
                            Created at
                        </th>
                        <th>
                            Actions
                        </th>
                    </tr>
                    <tr className={cx('filter')}>
                        <th>
                            &nbsp;
                        </th>
                        <th>
                            <input value={filterState.username} onChange={(e) => setFilterState({
                                ...filterState,
                                username: e.target.value
                            })} autoComplete='off' type="text" name="username" spellCheck={false} className={cx('filter-field')} />
                        </th>
                        <th>
                            <input value={filterState.role} onChange={(e) => setFilterState({
                                ...filterState,
                                role: e.target.value
                            })} type="text" name="role" spellCheck={false} className={cx('filter-field')} />
                        </th>
                        <th>
                            <input value={filterState.branchName} onChange={(e) => setFilterState({
                                ...filterState,
                                branchName: e.target.value
                            })} type="text" name="branchName" spellCheck={false} className={cx('filter-field')} />
                        </th>
                        <th>
                            <input value={filterState.createdAt} onChange={(e) => setFilterState({
                                ...filterState,
                                createdAt: e.target.value
                            })} type="date" name="Created at" spellCheck={false} className={cx('filter-field')} />
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {
                        accounts.map((account, index) => (
                            <tr key={index}>
                                <td>{index + 1}</td>
                                <td>{account.username}</td>
                                <td>{account.roleName}</td>
                                <td>{account.branchName}</td>
                                <td>{account.createdAt}</td>
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
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>New Account</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <FormNewAccount state={newAccount} setState={setNewAccount} />
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Close
                </Button>
                <Button onClick={handleCreateAccount} variant="primary">
                    Create
                </Button>
            </Modal.Footer>

        </Modal>
    </div>);
}

export default UserPage;