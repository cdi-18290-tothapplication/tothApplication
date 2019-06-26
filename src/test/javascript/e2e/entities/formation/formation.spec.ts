/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FormationComponentsPage, FormationDeleteDialog, FormationUpdatePage } from './formation.page-object';

const expect = chai.expect;

describe('Formation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let formationUpdatePage: FormationUpdatePage;
  let formationComponentsPage: FormationComponentsPage;
  let formationDeleteDialog: FormationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Formations', async () => {
    await navBarPage.goToEntity('formation');
    formationComponentsPage = new FormationComponentsPage();
    await browser.wait(ec.visibilityOf(formationComponentsPage.title), 5000);
    expect(await formationComponentsPage.getTitle()).to.eq('tothApplicationApp.formation.home.title');
  });

  it('should load create Formation page', async () => {
    await formationComponentsPage.clickOnCreateButton();
    formationUpdatePage = new FormationUpdatePage();
    expect(await formationUpdatePage.getPageTitle()).to.eq('tothApplicationApp.formation.home.createOrEditLabel');
    await formationUpdatePage.cancel();
  });

  it('should create and save Formations', async () => {
    const nbButtonsBeforeCreate = await formationComponentsPage.countDeleteButtons();

    await formationComponentsPage.clickOnCreateButton();
    await promise.all([
      formationUpdatePage.setLabelInput('label'),
      formationUpdatePage.setDescInput('desc')
      // formationUpdatePage.ccpSelectLastOption(),
    ]);
    expect(await formationUpdatePage.getLabelInput()).to.eq('label', 'Expected Label value to be equals to label');
    expect(await formationUpdatePage.getDescInput()).to.eq('desc', 'Expected Desc value to be equals to desc');
    await formationUpdatePage.save();
    expect(await formationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await formationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Formation', async () => {
    const nbButtonsBeforeDelete = await formationComponentsPage.countDeleteButtons();
    await formationComponentsPage.clickOnLastDeleteButton();

    formationDeleteDialog = new FormationDeleteDialog();
    expect(await formationDeleteDialog.getDialogTitle()).to.eq('tothApplicationApp.formation.delete.question');
    await formationDeleteDialog.clickOnConfirmButton();

    expect(await formationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
