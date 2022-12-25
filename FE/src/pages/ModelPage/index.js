import classNames from "classnames/bind";
import { useEffect, useState } from "react";
import { get } from "../../utils/request";
import style from "./ModelPage.module.scss";
import Pagination from "react-pagination-library"
import "react-pagination-library/build/css/index.css";

const cx = classNames.bind(style);

function ModelPage() {

    const [models, setModels] = useState([]);
    const [pageState, setPageState] = useState({
        currentPage: 1,
        totalPages: 0
    })

    useEffect(() => {
        get(`/api/v1/models?page=${pageState.currentPage - 1}`)
            .then(response => {
                console.log(response);
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


    return ( <div className={cx('wrapper')}>
    <div className={cx('header')}>
        <h1 className={cx('model-header')}>
            Product Model
        </h1>
        {/* <div className={cx('new-account')}>
            <button className={cx('btn-create')} onClick={handleOpen}>
                <FontAwesomeIcon icon={faPlus} className={cx('icon')} />
                <span>New Account</span>
            </button>
        </div> */}
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
                            <td>{model.colorString}</td>
                            <td>{model.capacityList.map((capacity, index)=> (<span style={{display: 'block'}} key={index}>{capacity}GB</span>))}</td>
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

</div> );
}

export default ModelPage;