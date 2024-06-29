import React, { useState } from 'react';
import './Login.css';
import {
  Container,
  Row,
  Col,
  Button,
  Form,
  FormGroup,
  Label,
  Input,
} from 'reactstrap';
import { useNavigate } from 'react-router-dom';

function LoginRegister() {
  const navigate = useNavigate();
  const [isLogin, setIsLogin] = useState(true);
  const [isOtpSent, setIsOtpSent] = useState(false);
  const [formData, setFormData] = useState({
    userName: '',
    mobile: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: ''
  });
  const [otp, setOtp] = useState('');
  const [emailForOtp, setEmailForOtp] = useState('');
  const [error, setError] = useState('');

  const toggleForm = () => {
    setIsLogin(!isLogin);
    setIsOtpSent(false);
    setFormData({
      userName: '',
      mobile: '',
      email: '',
      password: '',
      confirmPassword: '',
      role: ''
    });
    setOtp('');
    setEmailForOtp('');
    setError('');
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleOtpChange = (e) => {
    setOtp(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!isLogin && !isOtpSent) {
      if (formData.password !== formData.confirmPassword) {
        setError('Passwords do not match');
        return;
      }
    }

    const url = isLogin
      ? 'http://localhost:3000/api/login'
      : isOtpSent
      ? 'http://localhost:3000/api/verify-registration-otp'
      : 'http://localhost:3000/api/register';

    const payload = isOtpSent ? { email: emailForOtp, otp } : formData;

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message || 'Something went wrong');
      }

      console.log(data);

      if (!isLogin && !isOtpSent) {
        setIsOtpSent(true);
        setEmailForOtp(formData.email);
      } else if (isOtpSent) {
        setIsOtpSent(false);
        setFormData({
          userName: '',
          mobile: '',
          email: '',
          password: '',
          confirmPassword: '',
          role: ''
        });

        // Redirect to login after successful registration OTP verification
        navigate('/login');
      } else {
        // Redirect to dashboard based on role after successful login
        if (data.role === 'ROLE_ADMIN') {
          navigate('/admin-dashboard');
        } else {
          navigate('/user-dashboard');
        }
      }

      setError('');

    } catch (error) {
      console.error('Error:', error);
      setError(error.message);
    }
  };

  return (
    <Container fluid className="p-3 my-5">
      <Row>
        <Col md="6" className="d-none d-md-block">
          <img
            src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg"
            className="img-fluid"
            alt="Phone"
          />
        </Col>

        <Col md="6">
          {isLogin ? (
            <>
              <h2 className="mb-5">Login</h2>
              <Form onSubmit={handleSubmit}>
                <FormGroup>
                  <Label for="loginEmail">Email</Label>
                  <Input
                    type="email"
                    name="email"
                    id="loginEmail"
                    placeholder="Email address"
                    value={formData.email}
                    onChange={handleChange}
                  />
                </FormGroup>
                <FormGroup>
                  <Label for="loginPassword">Password</Label>
                  <Input
                    type="password"
                    name="password"
                    id="loginPassword"
                    placeholder="Password"
                    value={formData.password}
                    onChange={handleChange}
                  />
                </FormGroup>
                <FormGroup check className="mb-4">
                  <Label check>
                    <Input type="checkbox" /> Remember me
                  </Label>
                  <a href="#!" className="float-end">Forgot password?</a>
                </FormGroup>
                <Button color="primary" block>Sign in</Button>
                <div className="text-center my-4">
                  <span className="mx-2">OR</span>
                </div>
              </Form>
              <p className="text-center mt-4">
                Not a member? <a href="#!" onClick={toggleForm}>Register</a>
              </p>
            </>
          ) : (
            <>
              <h2 className="mb-5">{isOtpSent ? 'Verify OTP' : 'Register'}</h2>
              <Form onSubmit={handleSubmit}>
                {isOtpSent ? (
                  <>
                    <FormGroup>
                      <Label for="otp">OTP</Label>
                      <Input
                        type="text"
                        name="otp"
                        id="otp"
                        placeholder="Enter OTP"
                        value={otp}
                        onChange={handleOtpChange}
                      />
                    </FormGroup>
                    {error && <p className="text-danger">{error}</p>}
                    <Button color="primary" block>Verify OTP</Button>
                  </>
                ) : (
                  <>
                    <FormGroup>
                      <Label for="registerName">Full Name</Label>
                      <Input
                        type="text"
                        name="userName"
                        id="registerName"
                        placeholder="Full Name"
                        value={formData.userName}
                        onChange={handleChange}
                      />
                    </FormGroup>
                    <FormGroup>
                      <Label for="registerMobile">Mobile Number</Label>
                      <Input
                        type="text"
                        name="mobile"
                        id="registerMobile"
                        placeholder="Mobile Number"
                        value={formData.mobile}
                        onChange={handleChange}
                      />
                    </FormGroup>
                    <FormGroup>
                      <Label for="registerEmail">Email</Label>
                      <Input
                        type="email"
                        name="email"
                        id="registerEmail"
                        placeholder="Email address"
                        value={formData.email}
                        onChange={handleChange}
                      />
                    </FormGroup>
                    <FormGroup>
                      <Label for="registerPassword">Password</Label>
                      <Input
                        type="password"
                        name="password"
                        id="registerPassword"
                        placeholder="Password"
                        value={formData.password}
                        onChange={handleChange}
                      />
                    </FormGroup>
                    <FormGroup>
                      <Label for="confirmPassword">Confirm Password</Label>
                      <Input
                        type="password"
                        name="confirmPassword"
                        id="confirmPassword"
                        placeholder="Confirm Password"
                        value={formData.confirmPassword}
                        onChange={handleChange}
                      />
                    </FormGroup>
                    {error && <p className="text-danger">{error}</p>}
                    <Button color="primary" block>Sign up</Button>
                  </>
                )}
              </Form>
              <p className="text-center mt-4">
                {isOtpSent ? (
                  <a href="#!" onClick={toggleForm}>Go back to Registration</a>
                ) : (
                  <>
                    Already a member? <a href="#!" onClick={toggleForm}>Login</a>
                  </>
                )}
              </p>
            </>
          )}
        </Col>
      </Row>
    </Container>
  );
}

export default LoginRegister;
