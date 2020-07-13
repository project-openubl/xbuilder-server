import React from "react";
import Layout from "@theme/Layout";
import useDocusaurusContext from "@docusaurus/useDocusaurusContext";
import SwaggerUI from "swagger-ui-react";
import "swagger-ui-react/swagger-ui.css";

function Home() {
  const context = useDocusaurusContext();
  const { siteConfig = {} } = context;
  return (
    <Layout
      title={`${siteConfig.title}`}
      description="Java library for creating and signing XML files based on Universal Bussiness Language (UBL) <head />"
    >
      <main>
        <section>
          <div className="container">
            <SwaggerUI
              url="https://raw.githubusercontent.com/project-openubl/xbuilder-server/master/tools/openapi.json"
              docExpansion="list"
            />
          </div>
        </section>
      </main>
    </Layout>
  );
}

export default Home;
