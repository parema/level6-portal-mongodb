import { NgModule } from '@angular/core';

import { Level6SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [Level6SharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [Level6SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class Level6SharedCommonModule {}
