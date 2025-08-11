import React from 'react'
import { Link } from 'react-router-dom'
import { assets } from '../assets/assets'
import axios from 'axios';
import { toast } from 'react-toastify';
import { AppContext } from '../context/AppContext'
import { useNavigate } from 'react-router-dom'  

function Login() {

  const [isAccountCreated, setIsAccountCreated] = React.useState(false);  
  const [name, setName] = React.useState('');
  const [email, setEmail] = React.useState('');
  const [password, setPassword] = React.useState('');     
  const [loading, setLoading] = React.useState(false);
  const {backendUrl, setIsLoggedIn, getUserData} = React.useContext(AppContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true); 
    axios.defaults.withCredentials = true;
    try{
      if(isAccountCreated) {
        // reister API call
        const response = await axios.post(`${backendUrl}/register`, {
          name,
          email,
          password
        })
        if(response.status === 201) {
          // Handle successful registration (e.g., show a toast notification)
          navigate('/');
          toast.success('Account created successfully! Please login.');
        } else {
          // Handle error (e.g., show a toast notification)
          toast.error('Failed to create account. Please try again.');
        }
        
      } else {
        // Login Logic
        const response = await axios.post(`${backendUrl}/auth/login`, {
          email,
          password
        }, {withCredentials: true});
        if(response.status === 200) {
          // Handle successful login
          setIsLoggedIn(true);
          getUserData(); 
          navigate('/');
          toast.success('Login successful!');
        } else {
          // Handle error (e.g., show a toast notification)
          toast.error('Login failed. Please check your credentials.');    
        }
      }
    } catch (error) {
      // Handle error (e.g., show a toast notification)
      toast.error(error.response?.data?.message || 'An error occurred. Please try again.');
    } finally {
      setLoading(false);
    }
  } 

  return (
    <div className='position-relative min-vh-100 d-flex justify-content-center align-items-center'
         style={{ background: 'linear-gradient(90deg, #4e40c6ff, #8268f9)', border: 'none' }}>

          <div style={{position: 'absolute', top:'20px', left:'30px', display:'flex', alignItems:'center'}}>
            <Link to='/' style={{
              display: 'flex',
              gap: 5,
              alignItems: 'center',
              fontWeight: 'bold',
              fontSize: '24px',
              textDecoration: 'none',
            }}>
              <img src={assets.logo} alt='logo' height={32} width={32}></img>
              <span className='fw-bold fs-4 text-light'> Authify</span>
            </Link>

          </div>

          <div className='bg-white p-5 rounded shadow' style={{ width: '400px', minHeight: '400px' }}>
            <h2 className='text-center mb-4'>
              {isAccountCreated ? 'Create Account' : 'Login'}
            </h2>

            <form onSubmit={handleSubmit} className='d-flex flex-column gap-3'>
              {
                isAccountCreated && (
                  <div className='mb-3'>
                    <label htmlFor='name' className='form-label'>Name</label>
                    <input type='text' className='form-control' id='name' placeholder='Enter your name' required 
                    onChange={(e) => setName(e.target.value)}
                    value={name}/>
                  </div>
                )
              }

              <div className='mb-3'>
                <label htmlFor='email' className='form-label'>Email address</label>
                <input type='text' className='form-control' id='email' placeholder='Enter your email' required 
                onChange={(e) => setEmail(e.target.value)}
                value={email}/>    
              </div>

              <div className='mb-3'>
                <label htmlFor='password' className='form-label'>Password</label>
                <input type='password' className='form-control' id='password' placeholder='*********' required 
                onChange={(e) => setPassword(e.target.value)}
                value={password}/>
              </div>

              <div className='d-flex justify-content-between align-items-center mb-3'>
                <Link to='/reset-password' className='text-decoration-none text-secondary'>Forgot Password?</Link>
              </div>

              <button type='submit' className='btn btn-primary w-100' disabled={loading}>
                {loading ? 'Loading...' : isAccountCreated ? 'Sign Up' : 'Login'}
              </button>
              
              <div className='text-center mt-3'>
                <p className='mb-0'>
                  {isAccountCreated ? 
                  (
                    <>
                      Already have an account?{" "}
                      <span className='text-decoration-underline' style={{ cursor: 'pointer' }} onClick={() => setIsAccountCreated(false)}>
                        Login
                      </span>
                    </>
                  ): (
                    <>
                      Don't have an account?{" "}
                      <span className='text-decoration-underline' style={{ cursor: 'pointer' }} onClick={() => setIsAccountCreated(true)}>
                        Create Account
                      </span>
                    </>
                  )}
                </p>
              </div>
            </form>
          </div>

    </div>
  )
}

export default Login