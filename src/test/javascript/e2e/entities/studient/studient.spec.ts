/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StudientComponentsPage, StudientDeleteDialog, StudientUpdatePage } from './studient.page-object';

const expect = chai.expect;

describe('Studient e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let studientUpdatePage: StudientUpdatePage;
  let studientComponentsPage: StudientComponentsPage;
  let studientDeleteDialog: StudientDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Studients', async () => {
    await navBarPage.goToEntity('studient');
    studientComponentsPage = new StudientComponentsPage();
    await browser.wait(ec.visibilityOf(studientComponentsPage.title), 5000);
    expect(await studientComponentsPage.getTitle()).to.eq('tothApplicationApp.studient.home.title');
  });

  it('should load create Studient page', async () => {
    await studientComponentsPage.clickOnCreateButton();
    studientUpdatePage = new StudientUpdatePage();
    expect(await studientUpdatePage.getPageTitle()).to.eq('tothApplicationApp.studient.home.createOrEditLabel');
    await studientUpdatePage.cancel();
  });

  it('should create and save Studients', async () => {
    const nbButtonsBeforeCreate = await studientComponentsPage.countDeleteButtons();

    await studientComponentsPage.clickOnCreateButton();
    await promise.all([
      studientUpdatePage.setBirthdateInput('2000-12-31'),
      studientUpdatePage.userSelectLastOption(),
      studientUpdatePage.photoSelectLastOption()
    ]);
    expect(await studientUpdatePage.getBirthdateInput()).to.eq('2000-12-31', 'Expected birthdate value to be equals to 2000-12-31');
    await studientUpdatePage.save();
    expect(await studientUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await studientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Studient', async () => {
    const nbButtonsBeforeDelete = await studientComponentsPage.countDeleteButtons();
    await studientComponentsPage.clickOnLastDeleteButton();

    studientDeleteDialog = new StudientDeleteDialog();
    expect(await studientDeleteDialog.getDialogTitle()).to.eq('tothApplicationApp.studient.delete.question');
    await studientDeleteDialog.clickOnConfirmButton();

    expect(await studientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
