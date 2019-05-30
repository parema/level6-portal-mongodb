import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { Level6SharedLibsModule, Level6SharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [Level6SharedLibsModule, Level6SharedCommonModule],
  declarations: [HasAnyAuthorityDirective],
  exports: [Level6SharedCommonModule, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Level6SharedModule {
  static forRoot() {
    return {
      ngModule: Level6SharedModule
    };
  }
}
