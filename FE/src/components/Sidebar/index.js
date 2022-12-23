import { faAngleDown, faAngleLeft, faBuildingCircleArrowRight, faChartLine, faCodeBranch, faDroplet, faIndustry, faPencil, faPuzzlePiece, faToolbox, faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import classNames from "classnames/bind";
import { createContext, useContext, useEffect, useReducer, useRef, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { images } from "../../assets/images";
import routes from "../../configs/routes";
import { AuthContext } from "../../context/UserContext";
import style from './Sidebar.module.scss';

const cx = classNames.bind(style)

const adminSidebar = [
    {
        group: 'Branch',
        items: [
            {
                icon: faCodeBranch,
                text: 'Branch',
                subs: [
                    {
                        icon: faIndustry,
                        text: 'Manufacturing Base',
                        to: routes.manufactoringbase,
                    },
                    {
                        icon: faBuildingCircleArrowRight,
                        text: 'Distributor Agent',
                        to: routes.distributoragent
                    },
                    {
                        icon: faToolbox,
                        text: 'Warranty Center',
                        to: routes.warrrantycenter
                    },
                    {
                        text: 'New Branch',
                        to: routes.branch
                    }
                         
                ]
            }
        ]
    },
    {
        group: 'User',
        items: [
            {
                icon: faUser,
                text: 'Accounts',
                to: routes.accounts
            }
        ]
    }
]

const sidebarFake = [
    {
        group: 'Theme',
        items: [
            {
                icon: faDroplet,
                text: 'Colors',
                to: '/1'
            },
            {
                icon: faPencil,
                text: 'Typography',
                to: '/2'
            }
        ]
    },
    {
        group: 'Components',
        items: [
            {
                icon: faPuzzlePiece,
                text: 'Base1',
                subs: [
                    {
                        text: 'Accordian',
                        to: '/3'
                    },
                    {
                        text: 'Breadcrumb',
                        to: '/4'
                    },
                    {
                        text: 'Cards',
                        to: '/5'
                    },
                ]
            },
            {
                icon: faPuzzlePiece,
                text: 'Base2',
                subs: [
                    {
                        text: 'Accordian',
                        to: '/6'
                    },
                    {
                        text: 'Breadcrumb',
                        to: '/7'
                    },
                    {
                        text: 'Cards',
                        to: '/8'
                    },
                ]
            }
        ]
    },
    {
        group: 'Theme',
        items: [
            {
                icon: faDroplet,
                text: 'Colors',
                to: '/9'
            },
            {
                icon: faPencil,
                text: 'Typography',
                to: '/10'
            }
        ]
    },
    {
        group: 'Components',
        items: [
            {
                icon: faPuzzlePiece,
                text: 'Base3',
                subs: [
                    {
                        text: 'Accordian',
                        to: '/11'
                    },
                    {
                        text: 'Breadcrumb',
                        to: '/12'
                    },
                    {
                        text: 'Cards',
                        to: '/13'
                    },
                ]
            },
            {
                icon: faPuzzlePiece,
                text: 'Base4',
                subs: [
                    {
                        text: 'Accordian',
                        to: '/14'
                    },
                    {
                        text: 'Breadcrumb',
                        to: '/15'
                    },
                    {
                        text: 'Cards',
                        to: '/16'
                    },
                ]
            }
        ]
    },
    {
        group: 'Components',
        items: [
            {
                icon: faPuzzlePiece,
                text: 'Base5',
                subs: [
                    {
                        text: 'Accordian',
                        to: '/17'
                    },
                    {
                        text: 'Breadcrumb',
                        to: '/18'
                    },
                    {
                        text: 'Cards',
                        to: '/19'
                    },
                ]
            },
            {
                icon: faPuzzlePiece,
                text: 'Base6',
                subs: [
                    {
                        text: 'Accordian',
                        to: '/20'
                    },
                    {
                        text: 'Breadcrumb',
                        to: '/21'
                    },
                    {
                        text: 'Cards',
                        to: '/22'
                    },
                ]
            }
        ]
    }
]

const initState = {
    subIsOpen: '',
    linkActive: '',
}
const sidebarReducer = (state = initState, action) => {
    switch (action.type) {
        case 'CHANGE_SUB_OPEN':
            if (state.subIsOpen === action.itemText) {
                return {
                    ...state,
                    subIsOpen: ''
                }
            }
            return {
                ...state,
                subIsOpen: action.itemText
            }
        case 'CHANGE_LINK_ACTIVE':
            return {
                ...state,
                linkActive: action.link
            }
        default:
            return state
    }
}

const SidebarContext = createContext();
function Sidebar() {
    
    const [sidebarState, dispatch] = useReducer(sidebarReducer, initState)

    const [minimumSidebar, setMinimumSidebar] = useState(false);

    const location = useLocation();

    const [userState, userDispatch] = useContext(AuthContext);

    useEffect(() => {
        dispatch({type: 'CHANGE_LINK_ACTIVE',
                    link: location.pathname})
    },[location])
    // console.log(location.pathname)

    // console.log(minimumSidebar);
    let sidebar = userState.userInfo.role === 'ADMIN' ? adminSidebar : sidebarFake;

    return ( 
        <SidebarContext.Provider value={[sidebarState, dispatch]}>
            <div className={cx('wrapper')}>
                <div className={cx('header-brand')}>
                    <Link className={cx('logo')} to={routes.root}>
                        <img src={images.logo.default} alt='logo' />
                    </Link>
                </div>
                <div className={cx('sidebar-content')}>
                    {
                        sidebar.map((group, index) => (
                            <GroupSidebar group={group} key={index} />
                        ))
                    }
                </div>
                <div className={cx('toggle-minimum-sidebar')} onClick={() => setMinimumSidebar(!minimumSidebar)}>
                    <FontAwesomeIcon icon={faAngleLeft} className={cx('icon-toggle')} />
                </div>
            </div>
        </SidebarContext.Provider>
     );
}


function GroupSidebar({group}) {
    

    return (
        <div className={cx('groupsidebar')}>
            <div className={cx('group-header')}>
                <span>{group.group}</span>
            </div>
            <div className={cx('group-items')}>
                {
                    group.items.map((item, index) =>(
                        <GroupItem  key={index} item={item}/>
                    ))
                }
            </div>
            
        </div>
    )
}

function GroupItem({item}) {

    const hasSubs = item.subs !== undefined

    const [sidebarState, dispatch] = useContext(SidebarContext);


    const _prop = {

    }
    let Comp = 'div';
    if (item.to) {
        _prop.to = item.to;
        Comp = Link;

    }

    const subRef = useRef(null);


    return (
        <div className={cx('group-item',[hasSubs && sidebarState.subIsOpen ===  item.text? 'sub-open' : ''])}>
            <Comp {..._prop} className={cx('group-item-main',[!hasSubs && sidebarState.linkActive === item.to ? 'active' : ''])} onClick={() => dispatch({type: 'CHANGE_SUB_OPEN', itemText: item.text})}>
                <FontAwesomeIcon icon={item.icon} className={cx('item-icon')}/>
                <span>{item.text}</span>
                {
                    hasSubs && (
                        <FontAwesomeIcon icon={faAngleDown} className={cx('icon-dropdown')} />
                    )
                }
            </Comp>
            {
                hasSubs && (
                    <div className={cx('sub-items')} ref={subRef} style={sidebarState.subIsOpen ===  item.text && subRef.current ? {height: subRef.current.scrollHeight + 'px'} : {}}>
                        {
                            item.subs.map((sub, index) => (
                                <Link to={sub.to} key={index} className={cx('sub-item', [sidebarState.linkActive === sub.to ? 'active' : ''])} >
                                    {sub.text}
                                </Link>
                            ))
                        }
                    </div>
                )
            }

        </div>
    )
}

export default Sidebar;