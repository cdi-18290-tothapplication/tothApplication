/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CCPComponentsPage, CCPDeleteDialog, CCPUpdatePage } from './ccp.page-object';

const expect = chai.expect;

describe('CCP e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cCPUpdatePage: CCPUpdatePage;
  let cCPComponentsPage: CCPComponentsPage;
  let cCPDeleteDialog: CCPDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CCPS', async () => {
    await navBarPage.goToEntity('ccp');
    cCPComponentsPage = new CCPComponentsPage();
    await browser.wait(ec.visibilityOf(cCPComponentsPage.title), 5000);
    expect(await cCPComponentsPage.getTitle()).to.eq('tothApplicationApp.cCP.home.title');
  });

  it('should load create CCP page', async () => {
    await cCPComponentsPage.clickOnCreateButton();
    cCPUpdatePage = new CCPUpdatePage();
    expect(await cCPUpdatePage.getPageTitle()).to.eq('tothApplicationApp.cCP.home.createOrEditLabel');
    await cCPUpdatePage.cancel();
  });

  it('should create and save CCPS', async () => {
    const nbButtonsBeforeCreate = await cCPComponentsPage.countDeleteButtons();

    await cCPComponentsPage.clickOnCreateButton();
    await promise.all([
      cCPUpdatePage.setTitleInput('title')
      // cCPUpdatePage.documentsSelectLastOption(),
    ]);
    expect(await cCPUpdatePage.getTitleInput()).to.eq('title', 'Expected Title value to be equals to title');
    await cCPUpdatePage.save();
    expect(await cCPUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cCPComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last CCP', async () => {
    const nbButtonsBeforeDelete = await cCPComponentsPage.countDeleteButtons();
    await cCPComponentsPage.clickOnLastDeleteButton();

    cCPDeleteDialog = new CCPDeleteDialog();
    expect(await cCPDeleteDialog.getDialogTitle()).to.eq('tothApplicationApp.cCP.delete.question');
    await cCPDeleteDialog.clickOnConfirmButton();

    expect(await cCPComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
