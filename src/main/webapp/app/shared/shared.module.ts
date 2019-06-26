import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TothApplicationSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [TothApplicationSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [TothApplicationSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TothApplicationSharedModule {
  static forRoot() {
    return {
      ngModule: TothApplicationSharedModule
    };
  }
}
