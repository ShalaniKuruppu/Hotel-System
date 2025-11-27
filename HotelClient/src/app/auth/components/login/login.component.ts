import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
   loginForm!: FormGroup;

   constructor(private fb: FormBuilder, private authService: AuthService , private message: NzMessageService,
    private router: Router
  ) { }

  ngOnInit(){
    this.loginForm = this.fb.group({
      email: [null,[Validators.required, Validators.email]],
      password: [null,[Validators.required]],
      
    });
  }

  submitForm(){
    this.authService.login(this.loginForm.value).subscribe(res=>{
        console.log(res);
    },error=>{
        this.message.error(`Bad credentials`, { nzDuration: 5000 });
    })
}

}