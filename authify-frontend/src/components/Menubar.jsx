import React from 'react'
import { assets } from '../assets/assets.js'
import { useNavigate } from 'react-router-dom'

function Menubar() {

  const navigate = useNavigate();

  return (
    <nav className='navbar bg-light px-5 py-3 d-flex justify-content-between align-items-center'>
        <div className='d-flex align-items-center gap-2'>
            <img src={assets.logo} alt='logo' className='img-fluid' style={{ width: '40px', height: '40px' }} />
            <span className='fs-4 fw-bold text-dark'> Authify</span>
        </div>

        <div className="btn btn-dark rounded-pill px-3">
            Login <i className="bi bi-arrow-right ms-2" onClick={() => navigate("/login")}></i>
        </div>

    </nav>
  )
}

export default Menubar