import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class StudientComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-studient div table .btn-danger'));
  title = element.all(by.css('jhi-studient div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class StudientUpdatePage {
  pageTitle = element(by.id('jhi-studient-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  birthdateInput = element(by.id('field_birthdate'));
  userSelect = element(by.id('field_user'));
  photoSelect = element(by.id('field_photo'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setBirthdateInput(birthdate) {
    await this.birthdateInput.sendKeys(birthdate);
  }

  async getBirthdateInput() {
    return await this.birthdateInput.getAttribute('value');
  }

  async userSelectLastOption(timeout?: number) {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async photoSelectLastOption(timeout?: number) {
    await this.photoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async photoSelectOption(option) {
    await this.photoSelect.sendKeys(option);
  }

  getPhotoSelect(): ElementFinder {
    return this.photoSelect;
  }

  async getPhotoSelectedOption() {
    return await this.photoSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class StudientDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-studient-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-studient'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
