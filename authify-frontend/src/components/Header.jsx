import React from 'react'
import { assets } from '../assets/assets'

function Header() {
  return (
    <div className='text-center d-flex flex-column align-items-center justify-content-center py-5 px-3' style={{minHeight: '80vh'}}>
      <img src={assets.logo} alt='header' className='img-fluid mb-3' style={{ width: '100px', height: '100px' }} />
      
      <h5 className='fw-semibold'>
        Hey developer <span role='img' aria-label='wave'>👋</span>
      </h5>

      <h1 className='fw-bold text-dark'>
        Welcome to Authify
      </h1>

      <p className='text-muted fs-5 mb-4' style={{maxWidth: '600px'}}>
        Let's start with a quick authentication setup. You can easily manage user authentication with our simple and secure solution.
      </p>

      <button className='btn btn-outline-dark rounded-pill px-4 py-2' >
        Get Started
      </button>
    </div>
  )
}

export default Header