import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { JhiLanguageHelper, User, UserService } from 'app/core';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html'
})
export class UserMgmtUpdateComponent implements OnInit {
  user: User;
  languages: any[];
  authorities: any[];
  isSaving: boolean;
  isTrainer: boolean;
  isStaff: boolean;
  isAdmin: boolean;
  isStudient: boolean;

  editForm = this.fb.group({
    id: [null],
    login: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*')]],
    firstName: ['', [Validators.maxLength(50)]],
    lastName: ['', [Validators.maxLength(50)]],
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    activated: [true],
    langKey: [],
    authorities: [],
    authorities_noStudient: [],
    isStudient: false,
    isTrainer: false,
    isStaff: false,
    isAdmin: false
  });

  constructor(
    private languageHelper: JhiLanguageHelper,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.route.data.subscribe(({ user }) => {
      this.user = user.body ? user.body : user;
      this.updateForm(this.user);
    });
    this.authorities = [];
    this.userService.authorities().subscribe(authorities => {
      this.authorities = authorities;
    });
    if (this.user.authorities !== null) {
      this.isStudient = this.user.authorities.indexOf('ROLE_STUDIENT') !== -1;
      this.isTrainer = this.user.authorities.indexOf('ROLE_TRAINER') !== -1;
      this.isStaff = this.user.authorities.indexOf('ROLE_STAFF') !== -1;
      this.isAdmin = this.user.authorities.indexOf('ROLE_ADMIN') !== -1;
    }
    this.languageHelper.getAll().then(languages => {
      this.languages = languages;
    });
  }

  private updateForm(user: User): void {
    this.editForm.patchValue({
      id: user.id,
      login: user.login,
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      activated: user.activated,
      langKey: user.langKey,
      authorities: user.authorities,
      authorities_noStudient: user.authorities,
      isStudient: this.isStudient,
      isTrainer: this.isTrainer,
      isStaff: this.isStaff,
      isAdmin: this.isAdmin
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.updateUser(this.user);
    if (this.user.id !== null) {
      this.userService.update(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    } else {
      this.userService.create(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    }
  }

  private updateUser(user: User): void {
    user.login = this.editForm.get(['login']).value;
    user.firstName = this.editForm.get(['firstName']).value;
    user.lastName = this.editForm.get(['lastName']).value;
    user.email = this.editForm.get(['email']).value;
    user.activated = this.editForm.get(['activated']).value;
    user.langKey = this.editForm.get(['langKey']).value;
    user.authorities = ['ROLE_USER'];
    if (this.editForm.get(['authorities']).value === 'Studient') {
      user.authorities.push('ROLE_STUDIENT');
    } else {
      if (this.isTrainer) {
        user.authorities.push('ROLE_TRAINER');
      }
      if (this.isStaff) {
        user.authorities.push('ROLE_STAFF');
      }
      if (this.isAdmin) {
        user.authorities.push('ROLE_ADMIN');
      }
    }
  }
  private isStudientForm(): boolean {
    return this.editForm.get(['authorities']).value === 'Studient';
  }

  private onSaveSuccess(result) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
