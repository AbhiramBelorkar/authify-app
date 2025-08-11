import React from 'react'
import { assets } from '../assets/assets.js'
import { useNavigate } from 'react-router-dom'
import { AppContext } from '../context/AppContext'

function Menubar() {

  const navigate = useNavigate();
  const [dropdownOpen, setDropdownOpen] = React.useState(false);
  const dropdownRef = React.useRef(null);
  const {userData} = React.useContext(AppContext);

  return (
    <nav className='navbar bg-light px-5 py-3 d-flex justify-content-between align-items-center'>
        <div className='d-flex align-items-center gap-2'>
            <img src={assets.logo} alt='logo' className='img-fluid' style={{ width: '40px', height: '40px' }} />
            <span className='fs-4 fw-bold text-dark'> Authify</span>
        </div>

        {userData ? (
          <div className='position-relative' ref={dropdownRef}>
            <div className='bg-dark text-white rounded-circle d-flex justify-content-center align-items-center'
              style={{ width: '40px', height: '40px', cursor: 'pointer', userSelect: 'none' }}
              onClick={() => setDropdownOpen(!dropdownOpen)}>

                {userData.name[0].toUpperCase()}
              </div>

            {dropdownOpen && (
              <div className='dropdown-menu show position-absolute' style={{ top: '50px', right: '0', zIndex: 1000 }}>
                
                {!userData.isAccountVerified && (
                  <div className='dropdown-item py-2 px-3 text-danger'
                    style={{ cursor: 'pointer' }}>
                      Verify Email
                  </div>
                )} 
                <div className='dropdown-item py-2 px-3'
                  style={{ cursor: 'pointer' }}>
                    Logout
                </div>
              </div>
                )}
          </div>
        ) : (
          <div className="btn btn-dark rounded-pill px-3">
            Login <i className="bi bi-arrow-right ms-2" onClick={() => navigate("/login")}></i>
          </div>
        )}

        

    </nav>
  )
}

export default Menubar