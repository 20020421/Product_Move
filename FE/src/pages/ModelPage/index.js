import classNames from "classnames/bind";
import { useContext, useEffect, useState } from "react";
import { get } from "../../utils/request";
import style from "./ModelPage.module.scss";
import Pagination from "react-pagination-library"
import "react-pagination-library/build/css/index.css";
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Tooltip from 'react-bootstrap/Tooltip';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { Button, Modal } from "react-bootstrap";
import FormAddModel from "../../components/FormAddModel";
import { addModel } from "../../utils/requestJson";
import { AuthContext } from "../../context/UserContext";

const cx = classNames.bind(style);

function ModelPage() {

    const [models, setModels] = useState([]);
    const [pageState, setPageState] = useState({
        currentPage: 1,
        totalPages: 10
    })
    
    const [userState, dispatch] = useContext(AuthContext);
    console.log(userState)

    const [newModel, setNewModel] = useState({
        model: '',
        chip: '',
        colorString: [],
        capacityList: [],
    })

    console.log(models)

    

    useEffect(() => {
        get(`/api/v1/models?page=${pageState.currentPage - 1}`)
            .then(response => {
                setModels(response.models)
                setPageState({
                    ...pageState,
                    totalPages: response.totalPages
                })
            })
            .catch(error => console.log(error));
        // eslint-disable-next-line
    }, [pageState.currentPage])

    const changeCurrentPage = numPage => {
        setPageState({
            ...pageState,
            currentPage: numPage
        });
    }

    const[show, setShow] = useState(false);

    const handleOpen = () => {
        setShow(true);
    }

    const handleClose = () => {
        setShow(false);
    }

    const handleCreateModel = () => {
        addModel(newModel)
            .then(response => {

                handleClose()

                get(`/api/v1/models?page=${pageState.currentPage - 1}`)
            .then(response => {
                setModels(response.models)
                setPageState({
                    ...pageState,
                    totalPages: response.totalPages
                })
            })
            .catch(error => console.log(error));
            })
            .catch(error => console.log(error))
    } 

    return (<div className={cx('wrapper')}>
        <div className={cx('header')}>
            <h1 className={cx('model-header')}>
                Product Model
            </h1>
            <div className={cx('new-model')}>
                {
                    userState.userInfo.role === 'FACTORY' &&
            <button className={cx('btn-create')} onClick={handleOpen}>
                <FontAwesomeIcon icon={faPlus} className={cx('icon')} />
                <span>New Model</span>
            </button>
                }
        </div>
        </div>
        <div className={cx('table')}>
            <table className={cx('models')}>
                <thead>
                    <tr className={cx('header-tb')}>
                        <th>

                        </th>

                        <th>
                            Model
                        </th>
                        <th>
                            Chip
                        </th>
                        <th>
                            Colors
                        </th>
                        <th>
                            Capacities
                        </th>
                        <th>
                            Actions
                        </th>
                    </tr>

                </thead>
                <tbody>
                    {
                        models.map((model, index) => (
                            <tr key={index}>
                                <td>{index + 1}</td>
                                <td>{model.model}</td>
                                <td>{model.chip}</td>
                                <td><ModelColor model={model.model} /></td>
                                <td><ModelCapacity capacities={model.capacityList} /></td>
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
                <Modal.Title>New Model</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <FormAddModel state={newModel} setState={setNewModel} />
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Close
                </Button>
                <Button onClick={handleCreateModel}  variant="primary">
                    Create
                </Button>
            </Modal.Footer>

        </Modal>

    </div>);
}
function ModelColor({ model }) {

    const [colors, setColors] = useState([]);

    console.log(model)
    useEffect(() => {
        get(`/api/v1/models/${model}/colors`)
            .then(response => setColors(Object.entries(response)))
            .catch(error => console.log(error));
    }, [model])
    console.log(colors)

    return (<div className={cx('color-model')}>
        {
            colors.map((color, index) => (
                <OverlayTrigger
                    key={index}
                    placement='top'
                    overlay={
                        <Tooltip >
                            {color[0]}
                        </Tooltip>
                    }
                >
                    <div style={{ backgroundColor: `${color[1]}` }} key={index} className={cx('color')} />
                </OverlayTrigger>
            ))
        }
    </div>);
}

function ModelCapacity({ capacities }) {


    return (<div className={cx('capacities')}>
        {
            capacities.sort((a, b) => a - b).map((capacity, index) => {

                const capacityText = capacity >= 1024 ? `${capacity/1024}TB` : `${capacity}GB`;

                return (<div key={index} className={cx('capacity')}>
                    {capacityText}
                </div>)
            })
        }
    </div>);
}



export default ModelPage;