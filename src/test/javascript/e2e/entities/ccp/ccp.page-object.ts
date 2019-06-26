import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class CCPComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ccp div table .btn-danger'));
  title = element.all(by.css('jhi-ccp div h2#page-heading span')).first();

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

export class CCPUpdatePage {
  pageTitle = element(by.id('jhi-ccp-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  titleInput = element(by.id('field_title'));
  documentsSelect = element(by.id('field_documents'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTitleInput(title) {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput() {
    return await this.titleInput.getAttribute('value');
  }

  async documentsSelectLastOption(timeout?: number) {
    await this.documentsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async documentsSelectOption(option) {
    await this.documentsSelect.sendKeys(option);
  }

  getDocumentsSelect(): ElementFinder {
    return this.documentsSelect;
  }

  async getDocumentsSelectedOption() {
    return await this.documentsSelect.element(by.css('option:checked')).getText();
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

export class CCPDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cCP-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cCP'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
