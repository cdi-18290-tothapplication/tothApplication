import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TothApplicationSharedModule } from 'app/shared';
import {
  StudientComponent,
  StudientDetailComponent,
  StudientUpdateComponent,
  StudientDeletePopupComponent,
  StudientDeleteDialogComponent,
  studientRoute,
  studientPopupRoute
} from './';

const ENTITY_STATES = [...studientRoute, ...studientPopupRoute];

@NgModule({
  imports: [TothApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StudientComponent,
    StudientDetailComponent,
    StudientUpdateComponent,
    StudientDeleteDialogComponent,
    StudientDeletePopupComponent
  ],
  entryComponents: [StudientComponent, StudientUpdateComponent, StudientDeleteDialogComponent, StudientDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TothApplicationStudientModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
